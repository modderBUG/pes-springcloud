package me.xiaowei.modules.pes.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.domain.T_teacher;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.repository.*;
import me.xiaowei.modules.pes.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/69:58
 * Version:1.0
 * Desc:
 */
@Service
public class CouserServiceImpl implements CourseService {
    @Autowired
    T_experimentDAO t_experimentDao;

    @Autowired
    T_gradeDAO gradeDao;

    @Autowired
    private final T_timeDAO t_timeDao;

    @Autowired
    T_termDAO t_termDao;

    @Autowired
    T_teacherDAO t_teacherDao;

    @Autowired
    private final RedisTemplate redisTemplate;

    Lock lock = new ReentrantLock();

    public CouserServiceImpl(T_timeDAO t_timeDao, RedisTemplate redisTemplate) {
        this.t_timeDao = t_timeDao;
        this.redisTemplate = redisTemplate;

        //给Redis中写入课程限制缓存。
        initLimitData();
    }

    void initLimitData() {
        //全部限选实验列表
        List<Object> courseLimitList = t_timeDao.listCourseLimit();
        System.out.println(courseLimitList);
        for (Object item : courseLimitList) {
            JSONArray jsonItem = (JSONArray) JSON.toJSON(item);
            String courseLimit = jsonItem.get(0) + "_" + jsonItem.get(1);
            if (!redisTemplate.hasKey(courseLimit)) {
                redisTemplate.opsForValue().set(courseLimit, 0);
                System.out.println(jsonItem.get(0) + "_" + jsonItem.get(1));
            }
        }
    }

    @Override
    public ResponseEntity pickup(String expId, String username, String teacherName, String requestIp) {

        initLimitData();

        T_teacher teacherItem = null;

        try {
            String name = URLDecoder.decode(teacherName, "UTF-8");
            teacherItem = t_teacherDao.findByTeacherName(name);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //本实验限制在redis中字段。
        String courseLimit = expId + "_" + teacherItem.getTeacherId();
        JSONObject log = new JSONObject();

        if (!redisTemplate.hasKey(courseLimit)) {
            log.put("错误", "课程不存在或没有写入redis");
            return new ResponseEntity<>(log, HttpStatus.OK);
        }

        try {
            List<T_grade> list = gradeDao.findByStuNum(username);
            log.put("username", username);

            //已选过处理
            for (T_grade t : list) {
                if (t.getExpId().equals(expId)) {
                    log.put("err", "你选过此类课程");
                    return new ResponseEntity<>(log, HttpStatus.OK);
                }
            }

            //学期选课限制
            Integer limit = gradeDao.getMaxLimit(username);
            Integer limit_t = t_termDao.getOne(1).getWeekLimit();
            if (limit == null) {
                limit = 0;
            }

            if (limit >= limit_t/2) {
                log.put("err", "超过周选限制");
                return new ResponseEntity<>(log, HttpStatus.OK);
            }

            //学期选课限制
            HashSet<String> a = new HashSet<>();
            list.forEach(t_grade -> a.add(t_grade.getExpId()));
            if (a.size() > t_termDao.getOne(1).getTermLimit()) {
                log.put("err", "超过本学期限制");
                return new ResponseEntity<>(log, HttpStatus.OK);
            }

            //冲突限制
            List<T_time> timeTable = t_timeDao.findByExpIdAndTeacherId(expId,teacherItem.getTeacherId());
            if (timeTable.isEmpty()) {
                log.put("err", "老师还没有排这个课程");
                return new ResponseEntity<>(log, HttpStatus.OK);
            }
            for (T_time t : timeTable) {
                for (T_grade g : list) {
                    if (t.getExpTime().equals(g.getExpTime())) {
                        log.put("err", "时间冲突");
                        return new ResponseEntity<>(log, HttpStatus.OK);
                    }
                }
            }

            if (expId != null) {
                Integer target = t_experimentDao.findByExpId(expId).getExpLimit();
                Long now = 0L;

                if (redisTemplate.hasKey(courseLimit) == Boolean.TRUE) {
                    lock.lock();
                    try {
                        now = redisTemplate.opsForValue().increment(courseLimit, 1);
                        if (now > target) {
                            redisTemplate.opsForValue().decrement(courseLimit, 1);
                            log.put("err", "超出限制");
                            return new ResponseEntity<>(log, HttpStatus.OK);
                        }
                    } finally {
                        lock.unlock();
                    }

                    /**
                     * 鬼才把选课逻辑放在这里了。
                     * **/
                    List<T_grade> grades = new LinkedList<>();

                    timeTable.forEach(t_time -> {
                        T_grade obj = newGrade(username, expId, t_time, requestIp,teacherName);
                        grades.add(obj);
                    });
                    gradeDao.saveAll(grades);
                    log.put("msg", "选课成功:" + now + "\n总量:" + target);   //剩余
                } else {
                    log.put("err", "没有找到key值，注意大小写");
                }
            }
        } catch (Exception e) {
            log.put("err", e);
        }
        return new ResponseEntity<>(log, HttpStatus.OK);


    }


    @Override
    public ResponseEntity delete(String expId, String teacherName, String username) {

        T_teacher teacherItem = null;

        try {
            String name = URLDecoder.decode(teacherName, "UTF-8");
            teacherItem = t_teacherDao.findByTeacherName(name);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //本实验限制在redis中字段。
        String courseLimit = expId + "_" + teacherItem.getTeacherId();


        cn.hutool.json.JSONObject log = new cn.hutool.json.JSONObject();
        if (expId == null) {
            log.put("err", "参数不正确");
            return new ResponseEntity<>(log, HttpStatus.OK);
        }

        try {
            List<T_grade> T = gradeDao.findByStuNumAndExpId(username, expId);
            if (T.size() == 0) {
                log.put("err", "课程并不存在！");
                return new ResponseEntity<>(log, HttpStatus.OK);
            }
            long creat_time = T.get(0).getUpdateTime().getTime();
            long return_time = 1000 * 60 * 60 * t_termDao.getOne(1).getReturnLimit();  //
            if (System.currentTimeMillis() - creat_time > return_time) {
                log.put("err", "已经超过退课时间限制\n" + "\n选课时间:" + creat_time + "\n退课时间:" + System.currentTimeMillis());

                return new ResponseEntity<>(log, HttpStatus.OK);
            }

            gradeDao.deleteAll(T);
            redisTemplate.opsForValue().decrement(courseLimit, 1);

        } catch (Exception e) {
            log.put("err", e);
            return new ResponseEntity<>(log, HttpStatus.OK);
        }
        log.put("msg", "退课完成！");

        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @Override
    public List<T_grade> getStuScore(String username) {

        return gradeDao.findByStuNum(username);

    }


    @Override
    public void clearRedis() {
        List<Object> items = t_timeDao.listCourseLimit();

        List<String> courseLimit = new LinkedList<>();
        for (Object item : items) {
            JSONObject a = (JSONObject) JSON.toJSON(item);
            courseLimit.add(a.get(0) + "_" + a.get(1));
        }

        courseLimit.forEach(System.out::println);
    }


    private T_grade newGrade(String username, String expId, T_time t_time, String ip,String teacherId) {
        T_grade grade = new T_grade();
        grade.setStuNum(username);
        grade.setExpId(expId);
        grade.setTimeTimes(t_time.getTimeTimes().toString());
        grade.setTimeWeek(t_time.getTimeWeek().toString());
        grade.setScore(0.0);
        grade.setTestFlag(1);
        grade.setAttendance("缺席");
        grade.setIp(ip);
        grade.setSelfTest(0);
        grade.setExpTime(t_time.getExpTime());
        grade.setTeacherName(teacherId);
        return grade;
    }
}
