package springbooktest.learningtest.jdk;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
/**
 * Created by adaeng on 17/03/2019.
 */
public class ReflectionTest {

    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";

        assertThat(name.length(),is(6));

        Method lengthMethod = String.class.getMethod("length");
        assertThat((Integer)lengthMethod.invoke(name), is(6));

        //charAt()
        assertThat(name.charAt(0),is('S'));

        Method charAtMethod = String.class.getMethod("charAt",int.class);
        assertThat((Character) charAtMethod.invoke(name,0),is('S'));
    }

    @Test
    public void simpleProxy(){
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("to"),is("Hello to"));
        assertThat(hello.sayHi("to"),is("Hi to"));
        assertThat(hello.sayThankU("to"),is("Thank U to"));

        HelloUppercase helloUppercase = new HelloUppercase(hello); // 정적인 프록시
        assertThat(helloUppercase.sayHello("to"),is("HELLO TO"));
        assertThat(helloUppercase.sayHi("to"),is("HI TO"));
        assertThat(helloUppercase.sayThankU("to"),is("THANK U TO"));

        /*프록시를 사용했을 때의 장점
        * 1. 메소드의 적용이 한번에됨(똑같은 작업이 줄어듬)
        * 2. 타켓의 종류 상관없이 사용 가능
        *
        * */

        Hello proxieHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),new Class[] {Hello.class},
                new UpperCaseHandler(new HelloTarget()));

        assertThat(proxieHello.sayHello("to"),is("HELLO TO"));
        assertThat(proxieHello.sayHi("to"),is("HI TO"));
        assertThat(proxieHello.sayThankU("to"),is("THANK U TO"));
    }


}
