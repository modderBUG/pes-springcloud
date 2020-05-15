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
@Table(name = "t_student")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_student {
    @Id
    @Column(name="stu_num")
    private String stuNum;

    @Column(name="stu_name")
    private String stuName;

    @Column(name="stu_password")
    private String stuPassword;

    @Column(name="stu_class")
    private String stuClass;

    @Column(name="stu_email")
    private String stuEmail;

    @Column(name="stu_finger1")
    private String stuFinger1;

    @Column(name="stu_group")
    private String stuGroup;

    @Column(name="IC_num")
    private String ICNum;

    @Column(name="stu_tel")
    private String stuTel;

    @Column(name="IC_flag")
    private String ICFlag;

}
