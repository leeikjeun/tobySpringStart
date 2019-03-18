package springbook.user.factorybean;

/**
 * Created by adaeng on 18/03/2019.
 * 이와 같은 메소드는 평소와 같은 방법으로 빈에 등록이 안됨
 * <bean id ="m" class=...>
 *     ...</bean>
 * 스프링은 리플렉션 기능으로 오브젝트를 만들어주기는 하지만 생성자를 private로 만들어야 하는 이유가 있기떄문에
 * 강제로 생성하면 위험
 */
public class Message {
    private String text;

    private Message(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Message newMessge(String text){
        return new Message(text);
    }
}


