package me.xiaowei.modules.pes.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/918:21
 * Version:1.0
 * Desc:
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 注册STOMP协议节点endpoint并映射指定url
         * 添加一个访问端点/endpointMark，客户端大爱双通道时的url
         * 允许所有域名访问
         * **/
        registry.addEndpoint("/endpointMark")
                .setAllowedOrigins("*")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /** 配置消息代理 **/
        registry.enableSimpleBroker("/mass","/queue");
        /** 一对一，给用户信息时增加发送地址的前缀**/
        registry.setUserDestinationPrefix("/queue/");
    }
}
