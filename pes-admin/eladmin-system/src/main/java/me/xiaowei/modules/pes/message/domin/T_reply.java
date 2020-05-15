package me.xiaowei.modules.pes.message.domin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import me.xiaowei.modules.system.domain.UserAvatar;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "t_review")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class T_reply implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @ManyToOne(targetEntity = T_comment.class)   //评论ID
//    @JoinColumn(name = "comment_id",referencedColumnName = "id")
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "stuName")
    private String stuName;

    //    @Column(columnDefinition = "text")  //先不使用
    @Column(name = "editor_content", columnDefinition = "TEXT")
    private String editorContent;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;
}