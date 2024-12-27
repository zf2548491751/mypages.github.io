package com.example.demo.model;

public class ChatMessage {

    /*
    * 实体中，有三个字段：

type:消息类型
content：消息内容
sender：发送者
类型有三种：

CHAT: 消息
JOIN：加入
LEAVE：离开
* */
    private MessageType type;
    private String content;
    private String sender;

    /* public enum MessageType
    枚举类是一个特殊的类，，它一样有自己的成员变量、方法，可以实现一个或多个接口，也可以定义自己的构造器。
2、一个 java 源文件最多只能定义一个 public 访问权限的 枚举类。且该 java 源文件也必须和该枚举的类名相同
3、枚举类默认继承了 java.lang.Enum 类，而不是 Object 类，所以枚举类不能显示继承其他父类。其中
java.lang.Enum 类实现了 java.lang.Serializable 和 java.lang.Comparable 两个接口。
4、使用 enum 定义、非抽象的枚举类会默认使用 final 修饰
5、枚举类的构造器只能用 private 访问控制符，因为子类构造器总要调用父类构造器一次，所以枚举类不能派生子类
6、在枚举类中列出枚举值时，实际上就是调用构造器创建枚举类对象。只是这里无需使用 new 关键字。也无需显示调用
构造器。前面列出枚举值时无需传入参数（调用无参的构造方法），也可以传入参数（有参的构造方法）
7、枚举类的所有实例必须在枚举类的第一行显示列出，否则这个枚举类永远不能产生实例。列出这些实例时系统会自动
添加 public static final 修饰，无需自动添加
*/
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
