package me.xiaowei.modules.pes.rest;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import me.xiaowei.annotation.AnonymousAccess;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_freetime;
import me.xiaowei.modules.pes.service.FreetimeService;
import me.xiaowei.modules.pes.service.TimeService;
import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/612:57
 * Version:1.0
 * Desc:
 */
@RestController
@RequestMapping("/pes/api/teachertime")
public class FreetimeController {

    @Autowired
    FreetimeService freetimeService;


    @Log("教师抢课！")
    @ApiOperation("教师抢课")
    @GetMapping("/{id}")
    @AnonymousAccess
    public ResponseEntity pikeUpOne(@PathVariable("id") Integer id,
                                    @RequestParam(value = "teacherId", required = false) String teacherId
                                    ) {
        String username;
        JSONObject tips = new JSONObject();
        if (teacherId == null || "".equals(teacherId)) {
            username = SecurityUtils.getUsername();
        } else {
            username = teacherId;
        }
        /**
         * 先向抢课表写入数据。写入完成。再向时间表写入数据。
         * */
        T_freetime course = freetimeService.pikeUp(id, username);
        if (course == null) {
            tips.put("err", "课程已被其他老师选了，请选择其他课程。");
            return new ResponseEntity<>(tips, HttpStatus.OK);
        }
        tips.put("msg", "成功！");
        tips.put("data", course);
        return new ResponseEntity<>(tips, HttpStatus.OK);
    }

    @Log("查看有哪些可选")
    @ApiOperation("查看有哪些可选")
    @GetMapping
    @AnonymousAccess
    public ResponseEntity select(
            @RequestParam(value = "teacher", required = false) Boolean teacher,
            @RequestParam(value = "manager", required = false) Boolean manager,
            @RequestParam(value = "all", required = false) Boolean all,
            @RequestParam(value = "teacherName", required = false) String teacherName
    ) {
        if (teacher != null) {
            return new ResponseEntity<>(freetimeService.select(), HttpStatus.OK);
        }
        if (manager != null) {
            return new ResponseEntity<>(freetimeService.selectDTO(), HttpStatus.OK);
        }
        if (teacherName != null) {
            String teacherId= SecurityUtils.getUsername();
            return new ResponseEntity<>(freetimeService.selectByTeacherName(teacherId), HttpStatus.OK);
        }
        return new ResponseEntity<>(freetimeService.selectAll(), HttpStatus.OK);
    }

    @Log("排课不排教师")
    @ApiOperation("排课不排教师")
    @PostMapping
    @AnonymousAccess
    public ResponseEntity save(@RequestBody T_freetime t_freetime) {
        freetimeService.saveOne(t_freetime);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("删除一个排课")
    @ApiOperation("删除一个排课")
    @DeleteMapping("/{id}")
    @AnonymousAccess
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        freetimeService.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
