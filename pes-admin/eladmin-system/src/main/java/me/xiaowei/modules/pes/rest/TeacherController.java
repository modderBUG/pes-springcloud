package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_teacher;
import me.xiaowei.modules.pes.repository.T_teacherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pes/api/Teachers")
public class TeacherController {
    @Autowired
    T_teacherDAO t_teacherDao;

    @Log("获取全部教师列表")
    @ApiOperation("获取全部教师列表")
//    @GetMapping("/getAllTeachers")
    @GetMapping
    public List<T_teacher> getAllTeachers() {

        return t_teacherDao.findAll();
    }

    @Log("根据ID获取教师")
    @ApiOperation("根据ID获取教师")
//    @GetMapping("/getTeacher/{teacher_id}")
    @GetMapping("/{teacher_id}")
    public T_teacher getTeacher(@PathVariable("teacher_id") String teacher_id) {
        return t_teacherDao.getOne(teacher_id);
    }
}



