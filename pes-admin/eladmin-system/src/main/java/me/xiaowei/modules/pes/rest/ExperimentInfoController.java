package me.xiaowei.modules.pes.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.xiaowei.annotation.AnonymousAccess;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import me.xiaowei.modules.pes.repository.T_timeDAO;
import me.xiaowei.modules.pes.service.dto.ExperimentInfoDto;
import me.xiaowei.modules.pes.service.mapper.ExperimentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "实验时间、信息")
public class ExperimentInfoController {

    /**
     * 注入jpa
     */
    @Autowired
    private T_experimentDAO t_experimentDao;
    /**
     * jpa
     */
    @Autowired
    private T_timeDAO t_timeDao;
    /**
     * Mapper
     */
    @Autowired
    private ExperimentMapper experimentMapper;

    /**
     *
     */

//    @Log("查询实验时间对应实验名称，按周次timeTimes")
//    @ApiOperation("查询实验时间对应实验名称，按周次timeTimes")
//    @GetMapping(value = "/pes/api/findExperimentTimeByTimeTimes/{timeTimes}")
//    public List<ExperimentInfoDto> findExperimentByTimeTimes(@PathVariable("timeTimes") Integer timeTimes) {
//
//        List<T_time> t_timeList = t_timeDao.findByTimeTimes(timeTimes);
//        List<ExperimentInfoDto> list=new LinkedList<>();
//        for(T_time  t_time:t_timeList){
//            T_experiment t_experiment = t_experimentDao.findByExpId(t_time.getExpId());
//            list.add(experimentMapper.from(t_experiment, t_time));
//        }
//        return list;
//    }
    @Log("实验时间和实验表联合查询(多条件过滤)")
    @ApiOperation("实验时间和实验表联合查询")
    @GetMapping(value = "/pes/api/ExperimentAndTime")
    @AnonymousAccess
    public ResponseEntity findExperimentByTimeTimes(
            @RequestParam(value = "timeTimes", required = false) Integer timeTimes,
            @RequestParam(value = "expId", required = false) String expId,
            @RequestParam(value = "expName", required = false) String expName
    ) {
        List<Object> list = t_timeDao.getExpAndTime(timeTimes);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Log("查询实验时间对应实验名称，按实验id差")
    @ApiOperation("查询实验时间对应实验名称，按实验id差")
    @GetMapping(value = "/pes/api/findExperimentTimeByExpId/{expId}")
    public List<ExperimentInfoDto> detail(@PathVariable("expId") String expId) {

        List<T_time> t_timeList = t_timeDao.findByExpId(expId);
        List<ExperimentInfoDto> list = new LinkedList<>();
        for (T_time t_time : t_timeList) {
            T_experiment t_experiment = t_experimentDao.findByExpId(t_time.getExpId());
            list.add(experimentMapper.from(t_experiment, t_time));
        }

        return list;
    }

    @Log("查询实验时间对应实验名称，查全部")
    @ApiOperation("查询实验时间对应实验名称,查全部")
    @GetMapping(value = "/pes/api/findAllExperimentTime")
    public List<ExperimentInfoDto> findAllExperimentTime() {

        List<T_time> t_timeList = t_timeDao.findAll();
        List<ExperimentInfoDto> list = new LinkedList<>();

        for (T_time t_time : t_timeList) {
            T_experiment t_experiment = t_experimentDao.findByExpId(t_time.getExpId());
            list.add(experimentMapper.from(t_experiment, t_time));
        }

        return list;
    }
}
