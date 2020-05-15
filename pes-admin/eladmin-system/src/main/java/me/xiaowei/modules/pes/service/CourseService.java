package me.xiaowei.modules.pes.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/69:57
 * Version:1.0
 * Desc:
 */
public interface CourseService {


    //选择课程
    public ResponseEntity pickup(String expId, String stuNum, String teacherId, String requestIp);

    //退课
    public ResponseEntity delete(String expId,String teacherName, String username);

    //查看课程成绩
    List getStuScore(String username);

    //清除课程缓存
    public void clearRedis();


}
