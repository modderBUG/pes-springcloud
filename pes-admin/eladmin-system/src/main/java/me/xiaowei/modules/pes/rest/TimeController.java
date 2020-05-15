package me.xiaowei.modules.pes.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.repository.T_timeDAO;
import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/pes/api/TimeTable")
@RestController
public class TimeController {
    @Autowired
    private T_timeDAO t_timeDao;

    @Deprecated
    @Log("获取全部时间表内容")
    @ApiOperation("获取全部时间表内容")
    @GetMapping("/getAllTime")
    public List<T_time> getAllTime() {
        return t_timeDao.findAll();
    }

    @Deprecated
    @Log("根据周次查询实验时间(哪一周)")
    @ApiOperation("根据周次查询实验时间(哪一周)")
    @GetMapping("/getExperimentByTimeTimes/{timeTimes}")
    public List<T_time> getExperimentByTimeTimes(@PathVariable("timeTimes") Integer timeTimes) {
        return t_timeDao.findByTimeTimes(timeTimes);
    }

    @Log("根据实验查询时间")
    @ApiOperation("根据实验查询时间")
    @GetMapping("/{expId}")
    public List<T_time> getTimeByExpId(@PathVariable("expId") String expId) {
        return t_timeDao.findByExpId(expId);
    }


    @Log("实验时间条件查询")
    @ApiOperation("实验时间条件查询")
    @GetMapping
    public ResponseEntity getTimeByFilter(@RequestParam(value = "id", required = false) String id,
                                          @RequestParam(value = "time", required = false) String time,
                                          @RequestParam(value = "times", required = false) Integer times,
                                          @RequestParam(value = "week", required = false) Integer week,
                                          @RequestParam(value = "schedule", required = false) Integer schedule,
                                          @RequestParam(value = "teacher", required = false) String teacher,
                                          @RequestParam(value = "stuChannel", required = false) String stuChannel,
                                          @RequestParam(value = "returnChannel", required = false) Boolean returnChannel
    ) {
        //若是学生查询，则返回多表查询的DTO
        if (stuChannel != null) {
            if (returnChannel != null) {
//                return new ResponseEntity<>(t_timeDao.getStuExpAndTime(SecurityUtils.getUsername()), HttpStatus.OK);
                return new ResponseEntity<>(t_timeDao.listStudentCourseAndTeacher(SecurityUtils.getUsername()), HttpStatus.OK);
            }
            return new ResponseEntity<>(t_timeDao.getAllowExpAndTime(), HttpStatus.OK);
        }

//        System.out.println("|--"+id+"--|--"+time+"--|--"+times+"--|--"+week+"--|--"+schedule+"--|--"+teacher+"--|");
        List<T_time> target = new LinkedList<>();
        target = t_timeDao.findAll();
        target = id == null ? target : target.stream().filter(t_time -> t_time.getExpId().equals(id)).collect(Collectors.toList());
        target = time == null ? target : target.stream().filter(t_time -> t_time.getExpTime().equals(time)).collect(Collectors.toList());
        target = times == null ? target : target.stream().filter(t_time -> t_time.getTimeTimes().equals(times)).collect(Collectors.toList());
        target = week == null ? target : target.stream().filter(t_time -> t_time.getTimeWeek().equals(week)).collect(Collectors.toList());
        target = schedule == null ? target : target.stream().filter(t_time -> t_time.getTimeSchedule().equals(schedule)).collect(Collectors.toList());
        target = teacher == null ? target : target.stream().filter(t_time -> t_time.getTeacherId().equals(teacher)).collect(Collectors.toList());

        return new ResponseEntity<>(target, HttpStatus.OK);
    }

    @Log("从时间表删除全部实验")
    @ApiOperation("从时间表删除全部实验")
    @DeleteMapping
    public ResponseEntity delExpTime(@RequestParam(value = "expId", required = false) String expId) {
        List<T_time> list = t_timeDao.findByExpId(expId);

        t_timeDao.deleteAll(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Log("由试验时间表ID查询实验时间")
    @ApiOperation("由试验时间表ID查询实验时间")
    @GetMapping("/{id}")
    public T_time getTimeById(@PathVariable("id") Integer id) {
        return t_timeDao.getOne(id);
    }

    @Log("保存实验时间(一个)")
    @ApiOperation("保存实验时间(一个)")
    @PostMapping
    public T_time setTimeItem(@RequestBody T_time t_time) {
        return t_timeDao.save(t_time);
    }

    @Log("保存实验时间(json数组)")
    @ApiOperation("保存实验时间(json数组)")
    @PostMapping("/list")
    public List<T_time> setTimeList(@RequestBody List<T_time> items) {
        for (T_time item : items) {
            t_timeDao.save(item);
        }
        return items;
    }
}
