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
@Table(name = "t_freetime")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_freetime implements Serializable {

    /** 中间表
     *  通过此表教师抢课，将课程放入实验时间表
     *
     * **/
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="exp_id")
    private String expId;

    @Column(name="time_times")   //1-2-3-4-5-...-13
    private String timeTimes;

    @Column(name = "time_week") //3-5
    private String timeWeek;

    @Column(name = "time_schedule") //1-3 或2-4
    private String timeSchedule;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name="update_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;
}
