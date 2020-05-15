package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/923:29
 * Version:1.2
 * Desc:修改了请求方法，与前端对应
 */
@RestController
@RequestMapping("/pes/api/Experiments")
public class ExperimentController {
    @Autowired
    T_experimentDAO t_experimentDao;

    @Log("查询全部实验")
    @ApiOperation("查询全部实验")
//    @GetMapping("/getAllExperiment")
    @GetMapping
    public ResponseEntity getAllExperiment(@RequestParam(value = "expId", required = false) String expId) {

        if (expId != null) {
            return new ResponseEntity<>(t_experimentDao.findByExpId(expId), HttpStatus.OK);
        }

        return new ResponseEntity<>(t_experimentDao.findAll(), HttpStatus.OK);
    }

    @Log("查询实验{id}")
    @ApiOperation("查询实验{id}")
//    @GetMapping("/getExperiment/{id}")
    @GetMapping("/{id}")
    public ResponseEntity getExperiment(@PathVariable("id") Integer id) {

        return new ResponseEntity<>(t_experimentDao.getOne(id), HttpStatus.OK);


    }

    @Log("查询实验{expId}")
    @ApiOperation("查询实验{expId}")
    @GetMapping("/getExperimentById/{id}")
    public T_experiment test01(@PathVariable("id") String id) {
        return t_experimentDao.findByExpId(id);
    }

    @Log("保存一个实验")
    @ApiOperation("保存一个实验")
    @PostMapping("/setExperiment")
    public ResponseEntity setExperiment(@RequestBody T_experiment t_experiment) {

        return new ResponseEntity<>(t_experimentDao.save(t_experiment), HttpStatus.CREATED);
    }

}

