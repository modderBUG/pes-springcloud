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
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "t_comment")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_comment implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="stu_name")
    private String stuName;

//    @Column(columnDefinition = "text")  //先不使用
    @Column(name="editor_content",columnDefinition = "TEXT")
    private String editorContent;

    @Column(name="update_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;

    @ManyToOne(targetEntity = UserAvatar.class)
    @JoinColumn(name = "avatar_id",referencedColumnName = "id")
    private UserAvatar userAvatar;

    @OneToMany(targetEntity = T_reply.class)
    @JoinColumn(name = "comment_id",referencedColumnName = "id")
    private List<T_reply> reply;

}