package me.xiaowei.modules.pes.service.dto;

import lombok.Data;

@Data
public class GradeInfoDto {

    //桌号	学号	姓名	班级	考勤	预习	完成	成绩	操作

    private Integer deskId;

    private String stuNum;

    private String stuName;

    private String stuClass;

    private String attendance;

    private Integer testFlag;

    private Double score;
}
