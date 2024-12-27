package com.example.demo.listener;

/*Lombok是一个通过注解以达到减少代码的Java库,如通过注解的方式减少get,set方法,构造方法等。
* 几个常用的 lombok 注解：
@Data：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@Setter：注解在属性上；为属性提供 setting 方法
@Getter：注解在属性上；为属性提供 getting 方法
@SneakyThrows：无需在签名处显式抛出异常
@Log4j：注解在类上；为类提供一个 属性名为log 的 log4j 日志对像
@Slf4j: 同上
@NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
@AllArgsConstructor：注解在类上；为类提供一个全参的构造方法*/

import com.example.demo.model.ChatMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

//  我们已经在ChatController中定义的addUser（）方法中广播了用户加入事件。
//  因此，我们不需要在SessionConnected事件中执行任何操作。
//
//在SessionDisconnect事件中，编写代码用来从websocket会话中提取用户名，
// 并向所有连接的客户端广播用户离开事件


/*
* Spring ApplicationContext 是 Spring 保存对象实例的地方，
* Spring 已确定这些实例将被自动管理和分发。这些实例被称为 Bean。
Spring 的一些主要功能包括 Bean 管理和依赖注入。
利用控制反转（Inversion of Control），Spring 可以从 应用中收集 Bean 实例，
* 并在适当的时候使用它们。可以在 Spring 中定义 Bean 依赖，而无需处理这些对象的设置和实例化。
使用 @Autowired 等注解将 Spring 管理的 Bean 注入应用的能力是在 Spring 中创建功能强大且可扩展代码的驱动力。
那么，如何让 Spring 来管理的 Bean 呢？可以利用 Spring 的自动 Bean 检测功能，
* 通过在类中使用元注解（Stereotype Annotation）来实现。*/

/*
* @Component 是一个注解，它允许 Spring 自动检测自定义 Bean。

换句话说，无需编写任何明确的代码，Spring 就能做到：

扫描应用，查找注解为 @Component 的类
将它们实例化，并注入任何指定的依赖
在需要的地方注入
不过，大多数时候应该使用更专业的元（Stereotype）注解来实现这一功能。*/
/*
* Spring 提供了一些专门的元注解：@Controller、@Service 和 @Repository。
* 它们都提供了与 @Component 相同的功能。
它们的作用都是一样的，因为它们都是由 @Component 作为元注解组成的注解。
* 它们就像 @Component 别名，在  Spring 自动检测或依赖注入之外有专门的用途和意义。
理论上，可以只使用 @Component 来满足我们对 Bean 自动检测的需求。也可以编写使用 @Component 的专用注解。*/
/*
* @Component 是一个类级别的注解，而 @Bean 是方法级别的注解，
* 因此只有在类的源代码可编辑时，才可以使用 @Component 作为选项。@Bean 始终可以使用，但它的语法更加冗长。
@Component 与 Spring 的自动检测兼容，但 @Bean 需要手动实例化类。
使用 @Bean 可以将 Bean 的实例化与其类定义分离。
* 因此，可以使用它将第三方类转化为 Spring Bean。这也意味着可以添加逻辑来决定要使用哪个实例作为 Bean。*/
@Component
public class WebSocketEventListener {
//可以在IDE控制台打印日志
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
//监听连接
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }
//监听断开连接,获取离开用户名，并发送
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
