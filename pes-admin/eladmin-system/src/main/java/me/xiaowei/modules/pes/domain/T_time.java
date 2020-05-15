package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "t_time")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_time implements Serializable {



    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="exp_id")
    private String expId;

    @Column(name="exp_time")
    private String expTime;

    @Column(name="time_times")
    private Integer timeTimes;

    @Column(name = "timeWeek")
    private Integer timeWeek;

    @Column(name = "time_schedule")
    private Integer timeSchedule;

    @Column(name="teacher_id")
    private String teacherId;



}
