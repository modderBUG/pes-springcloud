package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "t_experiment")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_experiment implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="exp_id")
    private String expId;
    @Column(name="exp_name")
    private String expName;
    @Column(name="exp_class")
    private Integer expClass;
    @Column(name = "exp_lab")
    private String expLab;
    @Column(name = "exp_limit")
    private Integer expLimit;
    @Column(name="exp_term")
    private Integer expTerm;
    @Column(name="exp_group")
    private Integer expGroup;

}
