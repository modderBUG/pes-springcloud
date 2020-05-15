package me.xiaowei.modules.pes.rest;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiOperation;
import me.xiaowei.annotation.AnonymousAccess;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.service.CourseService;
import me.xiaowei.modules.pes.service.GradeService;
import me.xiaowei.utils.SecurityUtils;
import me.xiaowei.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Version:1.3
 * Desc:
 */

/**
 * 3.11
 * 1.没排的课不能选。
 * 4.06
 * 分离service层
 */
@RestController
@RequestMapping("/pes/api/course")
public class CourseController {

    @Autowired
    private final RedisTemplate redisTemplate;

    @Autowired
    CourseService courseService;

    @Autowired
    GradeService gradeService;

    public CourseController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //    @AnonymousAccess //http://127.0.0.1:8000/pes/api/course?expId=A101&stuNum=5120161{}
    @Log("选择课程")
    @ApiOperation("选择课程")
    @GetMapping
    public ResponseEntity select(@RequestParam(value = "expId", required = false) String expId,
                                 @RequestParam(value = "stuNum", required = false) String stuNum,
                                 @RequestParam(value = "teacherName", required = false) String teacherName,
                                 HttpServletRequest request) {
        String username;
        String requestIp = StringUtils.getIp(request);
        if (stuNum == null) {
            username = SecurityUtils.getUsername();
        } else {
            username = stuNum;
        }
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return courseService.pickup(expId, username, teacherName, requestIp);
    }


    @Log("查看课表")
    @ApiOperation("查看课表")
    @GetMapping("/schedule")
    public ResponseEntity getSchedule(@RequestParam(value = "stuNum", required = false) String stuNum,
                                      @RequestParam(value = "expId", required = false) String expId) {

        if (stuNum == null) {
            stuNum = SecurityUtils.getUsername();
        }


        Object[] table = gradeService.getClassSchedule(stuNum);

//        List<String> list = gradeDao.getAllExpId(stuNum);
//        List<T_time> t_timeList = new LinkedList<>();
//
//        for (String expId : list) {
//            t_timeList.addAll(t_timeDao.findByExpId(expId));
//        }
//
//        List<ClassScheduleDTO> dtos = new LinkedList<>();
//        for (T_time t_time : t_timeList) {
//            T_experiment t_experiment = t_experimentDao.findByExpId(t_time.getExpId());
//            dtos.add(classScheduleMapper.from(t_experiment, t_time));
//        }
//        dtos.forEach(classScheduleDTO -> {
//
//            String tid= classScheduleDTO.getTeacherName();
//
//            String tname =t_teacherDao.findByTeacherId(tid).getTeacherName();
//
//            classScheduleDTO.setTeacherName(tname);
//        });

        return new ResponseEntity<>(table, HttpStatus.OK);
    }


    @Log("查看课程成绩")
    @ApiOperation("查看课程成绩")
    @GetMapping("/Score")
    public ResponseEntity getStuScore() {
        String stuNum = SecurityUtils.getUsername();
        return new ResponseEntity<>(courseService.getStuScore(stuNum), HttpStatus.OK);
    }

    @Log("清除选课缓存")
    @ApiOperation("清除选课缓存")
    @DeleteMapping("/{id}")
    @AnonymousAccess
    public ResponseEntity crate(@PathVariable("id") String id) {

        JSONObject log = new JSONObject();
        try {
//            redisTemplate.opsForValue().set(id, 0);
//            log.put(id, redisTemplate.opsForValue().get(id));

            courseService.clearRedis();

        } catch (Exception e) {
            log.put("err", e);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("退课")
    @ApiOperation("退课")
    @DeleteMapping
    public ResponseEntity delete(@RequestParam(value = "expId", required = false) String expId,
                                 @RequestParam(value = "stuNum", required = false) String stuNum,
                                 @RequestParam(value = "teacherName", required = false) String teacherName
    ) {
        String username = SecurityUtils.getUsername();
        return courseService.delete(expId, teacherName, username);
    }
}
