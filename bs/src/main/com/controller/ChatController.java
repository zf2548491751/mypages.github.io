package src.main.com.controller;

import src.main.com.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    /*
    * 在 Spring Boot 中，@MessageMapping 注解用于标识一个 Java 方法是一个 WebSocket 消息处理器。
    * 消息处理器负责接收客户端发送的消息，并进行处理。
    * 具体来说，使用 @MessageMapping 注解的方法将会被 Spring WebSocket 自动扫描，并注册为一个消息处理器。
@MessageMapping 注解的作用类似于 @RequestMapping 注解，但是它只能用于 WebSocket 消息处理器。
* 使用 @MessageMapping 注解时，需要指定消息的目的地（Destination），即客户端发送消息时的目标地址。

* 我们在websocket配置中，
* 从目的地以/app开头的客户端发送的所有消息都将路由到这些使用@MessageMapping注释的消息处理方法。

例如，具有目标/app/chat.sendMessage的消息将路由到sendMessage（）方法，
* 并且具有目标/app/chat.addUser的消息将路由到addUser（）方法

    * */
    @MessageMapping("/chat.sendMessage") //用户发送
    @SendTo("/topic/public")            //转发至目标
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
/*
* payload是一种以JSON格式进行数据传输的一种方式。

http可能会传输payload，如果不限制其请求的方式(那么请求的方法就是OPTIONS)或者响应的状态码，
* 其包含元数据，头部区域和数据。

如果数据是通过正常的put或者post方法发送的，那么payload就是一个http请求起始行紧接一个CRLF后面的那一部分。

* */


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
