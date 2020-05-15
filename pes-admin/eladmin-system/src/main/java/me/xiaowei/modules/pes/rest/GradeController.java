package me.xiaowei.modules.pes.rest;


import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import me.xiaowei.modules.pes.repository.T_gradeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pes/api")
public class GradeController {
    @Autowired
    T_gradeDAO t_gradeDao;

    @Autowired
    T_experimentDAO t_experimentDao;

    @Log("根据实验查成绩")
    @ApiOperation("根据实验查成绩")
    @GetMapping("/getGradeByExperiment/{expId}")
    public List<T_grade> getGradeByExperiment(@PathVariable("expId") String expId) {

        return t_gradeDao.findByExpId(expId);
    }


    @Log("查询全部成绩")
    @ApiOperation("查询全部成绩")
    @GetMapping("/getAllGrade")
    public List<T_grade> getAllGrade() {
        return t_gradeDao.findAll();
    }


    @Log("由id查找实验成绩(成绩表里)")
    @ApiOperation("由id查找实验成绩(成绩表里)")
    @GetMapping("/getGrade/{id}")
    public T_grade getGrade(@PathVariable("id") Integer id) {
        return t_gradeDao.getOne(id);
    }

    @Log("由学生学号查找成绩（学生全部成绩）")
    @ApiOperation("由学生学号查找成绩（学生全部成绩）")
    @GetMapping("/getGradeById/{id}")
    public List<T_grade> getGradeById(@PathVariable("id") String id) {
        return t_gradeDao.findByExpId(id);
    }

    @Log("保存成绩到成绩表")
    @ApiOperation("保存成绩到成绩表")
    @PostMapping("/setGrade")
    public T_grade setGrade(@RequestBody T_grade t_grade) {

        return t_gradeDao.save(t_grade);
    }
}

