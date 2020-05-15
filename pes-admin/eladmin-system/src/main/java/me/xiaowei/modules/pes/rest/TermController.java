package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_term;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import me.xiaowei.modules.pes.repository.T_termDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pes/api")
@RestController
public class TermController {

    @Autowired
    private T_termDAO t_termDao;

    @Autowired
    private T_experimentDAO t_experimentDao;

    @Log("根据周次查实验(哪一周)")
    @ApiOperation("根据周次查实验(哪一周)")
    @GetMapping("/getExperimentWeeks/{Weeks}")
    public List<T_experiment> getExperimentWeeks(@PathVariable("Weeks") Integer times) {
/**
 * 这里需要做相关处理，本页全部需要！
 * **/
        return t_experimentDao.findAll();
    }


    @Log("根据星期查实验(星期几)")
    @ApiOperation("根据星期查实验(星期几)")
    @GetMapping("/getExperimentThisWeek/{week}")
    public List<T_experiment> getExperimentThisWeek(@PathVariable("week") Integer week) {

        return t_experimentDao.findAll();
    }


    @Log("获取全部试验时间")
    @ApiOperation("获取全部试验时间")
    @GetMapping("/getTermInfo")
    public List<T_term> getTermInfo() {
        return t_termDao.findAll();
    }

    @Log("设置实验学期相关属性")
    @ApiOperation("设置实验学期相关属性")
    @PutMapping("/setTermInfo")
    public T_term setTermInfo(@RequestBody T_term t_term) {
        T_term save = t_termDao.save(t_term);
        return save;
    }

}
