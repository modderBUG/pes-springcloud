package me.xiaowei.modules.pes.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import me.xiaowei.modules.pes.websocket.domin.ChatRoomRequest;
import me.xiaowei.modules.pes.websocket.domin.ChatRoomResponse;
import me.xiaowei.modules.pes.websocket.domin.T_message;
import me.xiaowei.modules.pes.websocket.repository.T_messageDao;
import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/918:54
 * Version:1.0
 * Desc:
 */

@Controller
@RestController

public class StompController {
    /** 群发消息 接收送到 massRequest的请求**/
    /** SendTo 发送至Broker 下的指定订阅路径mass**/
    /**
     * Broker 在发送消息到订阅了/mass的用户
     **/
    @MessageMapping("/massRequest")
    @SendTo("/mass")
    public T_message mass(T_message chatRoomRequest) {
        System.out.println(JSONObject.toJSONString(chatRoomRequest));
        T_message msg = new T_message();
        msg.setChatValue(chatRoomRequest.getChatValue());
        msg.setFromName(chatRoomRequest.getFromName());
        msg.setToUserId(chatRoomRequest.getToUserId());
        messageDao.save(msg);
        return msg;
    }

    @SubscribeMapping("/mass")
    public List<T_message> getGroupMessageChech() {
        // Here I would need the current user...
        List<T_message> list = messageDao.findMsgForGroup();
        return list;
    }

    /**
     * Spring实现一个发送模板类
     **/
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    T_messageDao messageDao;

    @MessageMapping("/alongRequest")
    public void alone(ChatRoomRequest chatRoomRequest) {
        System.out.println(JSONObject.toJSONString(chatRoomRequest));
        ChatRoomResponse response = new ChatRoomResponse();
        response.setFromName(chatRoomRequest.getFromName());
        response.setChatValue(chatRoomRequest.getChatValue());

        T_message msg = new T_message();
        msg.setChatValue(chatRoomRequest.getChatValue());
        msg.setFromName(chatRoomRequest.getFromName());
        msg.setToUserId(chatRoomRequest.getToUserId());

        messageDao.save(msg);
        this.template.convertAndSendToUser(chatRoomRequest.getToUserId(), "/alone", response);
    }

    @SubscribeMapping("/queue/{toUserId}/alone")
    public void subscribeMapping(@DestinationVariable("toUserId") String toUserId) {
        // Here I would need the current user...
        List<T_message> list = messageDao.findmsg(toUserId);
        if (list.size() == 0) {
            ChatRoomResponse response = new ChatRoomResponse();
            response.setFromName("系统");
            response.setChatValue("第一次来到聊天室。");
            this.template.convertAndSendToUser(toUserId, "/alone", response);
            return;
        }
        this.template.convertAndSendToUser(toUserId, "/alone", list);
    }
}
