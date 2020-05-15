package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "t_term")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(name="term_start")
    private Timestamp termStart;

    @NotNull
    @Column(name="exp_term")
    private Integer expTerm;

    @Column(name="term_weeks")
    private Integer termWeeks;

    @Column(name="week_limit")
    private Integer weekLimit;

    @Column(name="term_limit")
    private Integer termLimit;

    @Column(name="return_limit")
    private Integer returnLimit;



}
