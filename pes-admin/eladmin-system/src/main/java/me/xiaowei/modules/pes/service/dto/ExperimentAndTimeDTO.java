package me.xiaowei.modules.pes.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class ExperimentAndTimeDTO implements Serializable {
    //实验名称
    private String expName;

    private String expId;
    //周次
    private Integer timeTimes;
    //星期
    private Integer timeWeek;
    //时间段
    private Integer timeSchedule;
}
