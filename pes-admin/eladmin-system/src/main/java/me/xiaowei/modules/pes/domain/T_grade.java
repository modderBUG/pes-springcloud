package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "t_grade")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_grade implements Serializable {


    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="stu_num")
    private String stuNum;

    @Column(name="exp_id")
    private String expId;

    @Column(name="exp_time")
    private String expTime;

    @Column(name="time_times")
    private String timeTimes;

    @Column(name = "time_week")
    private String timeWeek;

    @Column(name = "score")
    private Double score;

    @Column(name="test_flag")
    private Integer testFlag;

    @Column(name="attendance")
    private String attendance;

    @Column(name="ip")
    private String ip;

    @Column(name="update_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;

    @Column(name="desk_id")
    private Integer deskId;

    @Column(name="att_time")
    private Timestamp attTime;

    @Column(name="fin_time")
    private Timestamp finTime;

    @Column(name="self_test")
    private Integer selfTest;

    @Column(name="teacher_name")
    private String teacherName;

}
