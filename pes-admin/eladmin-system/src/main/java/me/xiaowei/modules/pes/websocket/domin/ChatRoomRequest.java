package me.xiaowei.modules.pes.websocket.domin;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/918:56
 * Version:1.0
 * Desc:
 */
@Getter
@Setter
public class ChatRoomRequest implements Serializable {
    private  String fromName;
    private String chatValue;
    private String toUserId;
}
