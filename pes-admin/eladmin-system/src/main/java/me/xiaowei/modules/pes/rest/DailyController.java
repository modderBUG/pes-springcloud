package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.annotation.AnonymousAccess;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_daily;
import me.xiaowei.modules.pes.repository.T_dailyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/5/913:42
 * Version:1.0
 * Desc:
 */
@RestController
@RequestMapping("/pes/api/daily")
public class DailyController {

    @Autowired
    T_dailyDAO dailyDAO;

    @Log("获取全部推送消息")
    @ApiOperation("获取全部推送消息")
    @GetMapping
    @AnonymousAccess
    ResponseEntity getAll(@RequestParam(value = "limit", required = false) Integer limit){

        if(limit!=null&&limit>1){
            return new ResponseEntity<>(dailyDAO.findByLimit(limit), HttpStatus.OK);
        }
        return new ResponseEntity<>(dailyDAO.findAll(), HttpStatus.OK);
    }

    @Log("删除一条推送")
    @ApiOperation("删除一条推送")
    @DeleteMapping("/{id}")
    ResponseEntity delete( @PathVariable("id") Integer id){
        dailyDAO.deleteById(id);
        return new ResponseEntity<>("ok!", HttpStatus.OK);
    }

    @Log("新增一条推送")
    @ApiOperation("新增一条推送")
    @PostMapping
    ResponseEntity post(@RequestBody T_daily t_daily){
        dailyDAO.save(t_daily);
        return new ResponseEntity<>(t_daily, HttpStatus.OK);
    }
}
