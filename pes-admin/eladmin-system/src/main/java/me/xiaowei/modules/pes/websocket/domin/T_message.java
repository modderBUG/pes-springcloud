package me.xiaowei.modules.pes.websocket.domin;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/923:29
 * Version:1.0
 * Desc:
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "T_message")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class T_message {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fromName")
    private String fromName;
    @Column(name = "chatValue")
    private String chatValue;
    @Column(name = "toUserId")
    private String toUserId;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Timestamp updateTime;

}
