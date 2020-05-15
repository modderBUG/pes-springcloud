package me.xiaowei.modules.pes.rest;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.domain.T_student;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import me.xiaowei.modules.pes.repository.T_gradeDAO;
import me.xiaowei.modules.pes.repository.T_studentDAO;
import me.xiaowei.modules.pes.service.dto.ExperimentAndTimeDTO;
import me.xiaowei.modules.pes.service.dto.GradeInfoDto;
import me.xiaowei.modules.pes.service.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2019/12/1523:48
 * Version:1.0
 * Desc:
 */

@RestController
public class GradeInfoController {

    /**
     * 注入jpa
     */
    @Autowired
    private T_studentDAO t_studentDao;
    /**
     * jpa
     */
    @Autowired
    private T_gradeDAO t_gradeDao;

    @Autowired
    private T_experimentDAO t_experimentDao;
    /**
     * Mapper
     */
    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 根据学号查找成绩、姓名
     *
     * @param stuNum
     * @return list
     */
    @GetMapping(value = "/pes/api/getOneGrade/{stuNum}")
    public List<GradeInfoDto> detail(@PathVariable("stuNum") String stuNum) {
        List<T_grade> items = t_gradeDao.findByStuNum(stuNum);
        List<GradeInfoDto> list = new LinkedList<>();

        for (T_grade t_grade : items) {
            T_student t_student = t_studentDao.findByStuNum(t_grade.getStuNum());
            list.add(gradeMapper.from(t_student, t_grade));
        }

        //返回转换dto
        return list;
    }


    /**
     * 查询全部实验成绩（几乎用不到）
     *
     * @return list
     */
//    @GetMapping(value = "/pes/api/getAllGrade/")
    @GetMapping(value = "/pes/api/GradeInfoDTO/")
    public List<GradeInfoDto> findAllExperimentTime() {
        List<T_grade> items = t_gradeDao.findAll();
        List<GradeInfoDto> list = new LinkedList<>();

        for (T_grade t_grade : items) {
            T_student t_student = t_studentDao.findByStuNum(t_grade.getStuNum());
            list.add(gradeMapper.from(t_student, t_grade));
        }
        //返回转换dto
        return list;
    }


    /**
     * 根据实验时间、实验id，查询成绩
     *
     * @param t_time 一条实验时间
     * @return list
     */
    @PostMapping(value = "/pes/api/getGradeByTimesAndExpId/")
    public List<GradeInfoDto> getGradeByTimesAndExpId(@RequestBody T_time t_time) {
        List<T_grade> items = t_gradeDao.
                findByExpIdAndTimeTimesAndTimeWeek(
                        t_time.getExpId(),
                        t_time.getTimeTimes().toString(),
                        t_time.getTimeWeek().toString());

        List<GradeInfoDto> list = new LinkedList<>();

        for (T_grade t_grade : items) {
            T_student t_student = t_studentDao.findByStuNum(t_grade.getStuNum());
            list.add(gradeMapper.from(t_student, t_grade));
        }
        //返回转换dto
        return list;
    }


    /**
     * 根据实验时间、实验id，查询成绩
     *
     * @param experimentInfoDto 一条实验数据
     * @return list
     */
    @PostMapping(value = "/pes/api/GradeInfoDTO/")
    public List<GradeInfoDto> getGradeByDto(@RequestBody ExperimentAndTimeDTO experimentInfoDto) {


        System.out.println(experimentInfoDto);

        T_experiment t_experiment = t_experimentDao.findByExpName(experimentInfoDto.getExpName());


        List<T_grade> items = t_gradeDao.
                findByExpIdAndTimeTimesAndTimeWeek(
                        t_experiment.getExpId(),
                        experimentInfoDto.getTimeTimes().toString(),
                        experimentInfoDto.getTimeWeek().toString());

        List<GradeInfoDto> list = new LinkedList<>();

        for (T_grade t_grade : items) {
            T_student t_student = t_studentDao.findByStuNum(t_grade.getStuNum());
            list.add(gradeMapper.from(t_student, t_grade));
        }
        //返回转换dto
        return list;
    }


    /**
     * 传入一个LinkedHashMap，通过这个东西找到这个学生成绩条目，然后修改他。
     *
     * @param object LinkedHashMap 包含了三张表数据。精准定位实验成绩表。
     * @return list
     */
//    @PostMapping(value = "/pes/api/editGradeByDto/")
    @PutMapping(value = "/pes/api/GradeInfoDTO/")
    public T_grade editGradeByDto(@RequestBody LinkedHashMap<String, String> object) {
        T_experiment t_experiment = t_experimentDao.findByExpName(object.get("expName"));
        T_grade t_grade = t_gradeDao.findByExpIdAndTimeTimesAndTimeWeekAndStuNum(t_experiment.getExpId(),
                object.get("timeTimes"),
                object.get("timeWeek"),
                object.get("stuNum"));

        if (object.get("score") != null) {
            t_grade.setScore(Double.parseDouble(object.get("score")));
        }
        if (object.get("attendance") != null) {
            t_grade.setAttendance(object.get("attendance"));
        }
        if (object.get("deskId") != null) {
            t_grade.setDeskId(Integer.parseInt(object.get("deskId")));
        }
        // t_grade.setStuNum(object.get("stuNum"));
        if (object.get("testFlag") != null) {
            t_grade.setTestFlag(Integer.parseInt(object.get("testFlag")));
        }
        return t_gradeDao.save(t_grade);
    }
}
