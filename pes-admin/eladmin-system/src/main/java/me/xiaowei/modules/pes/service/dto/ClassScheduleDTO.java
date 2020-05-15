package me.xiaowei.modules.pes.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Data
public class ClassScheduleDTO implements Serializable {

    //实验名称 实验id 实验教室 实验时间 实验周次 实验星期 实验时段 教师id



    private String expName;

    private String expId;

    private String expLab;

    private String expTime;

    private Integer timeTimes;

    private Integer timeWeek;

    private Integer timeSchedule;

    private String teacherName;
}
