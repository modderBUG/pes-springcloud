package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_teacher")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_teacher {
    @Id
    @Column(name="teacher_id")
    private String teacherId;
    @Column(name="teacher_name")
    private String teacherName;
    @Column(name="teacher_password")
    private String teacherPassword;
    @Column(name="teacher_group")
    private String teacherGroup;
    @Column(name="teacher_email")
    private String teacherEmail;

}
