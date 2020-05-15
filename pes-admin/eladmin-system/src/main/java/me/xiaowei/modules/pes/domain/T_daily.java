package me.xiaowei.modules.pes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import me.xiaowei.modules.system.domain.UserAvatar;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/5/913:31
 * Version:1.0
 * Desc:
 */

@Entity
@Getter
@Setter
@Table(name = "t_daily")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_daily implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="editor_content",columnDefinition = "TEXT")
    private String editorContent;

    @Column(name="update_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;

    @Column(name = "editor_author", columnDefinition = "TEXT")
    private String editorAuthor;

}