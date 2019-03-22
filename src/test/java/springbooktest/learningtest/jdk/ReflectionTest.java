package springbooktest.learningtest.jdk;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

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
        assertThat(hello.sayThankU("to"),is("Thank u to"));

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


    @Test
    public void proxyFactroyBean(){
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());


        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,new UppercaseAdvice());

        pfBean.addAdvice(advisor.getAdvice());

        Hello proxieHello = (Hello) pfBean.getObject();

        assertThat(proxieHello.sayHello("to"),is("HELLO TO"));
        assertThat(proxieHello.sayHi("to"),is("HI TO"));
        assertThat(proxieHello.sayThankU("to"),is("THANK U TO"));
    }

    @Test
    public void classNamePointcutAdvisor(){

        NameMatchMethodPointcut classMAethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter(){
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> aClass) {
                        return aClass.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };

        classMAethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMAethodPointcut, true);

        class HelloWorld extends HelloTarget {};
        checkAdviced(new HelloWorld(), classMAethodPointcut, false);

        class HelloToby extends HelloTarget{};
        checkAdviced(new HelloToby(), classMAethodPointcut, true);

    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced){
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut,new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if(adviced){
            assertThat(proxiedHello.sayHello("to"),is("HELLO TO"));
            assertThat(proxiedHello.sayHi("to"),is("HI TO"));
            assertThat(proxiedHello.sayThankU("to"),is("Thank u to"));
        }else{
            assertThat(proxiedHello.sayHello("to"),is("Hello to"));
            assertThat(proxiedHello.sayHi("to"),is("Hi to"));
            assertThat(proxiedHello.sayThankU("to"),is("Thank u to"));
        }
    }



    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String)invocation.proceed();//리플렉션의 메소드와 달리 메소드 실행 시 타깃 오브젝트를 전달할 필요가 없다
            return ret.toUpperCase();
        }
    }
}
