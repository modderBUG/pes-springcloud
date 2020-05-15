package me.xiaowei.modules.pes.service.dto;

import lombok.Data;

@Data
public class ExperimentInfoDto {
    //实验名称
    private String expName;
    //周次
    private Integer timeTimes;
    //星期
    private Integer timeWeek;
    //时间段
    private Integer timeSchedule;
}
