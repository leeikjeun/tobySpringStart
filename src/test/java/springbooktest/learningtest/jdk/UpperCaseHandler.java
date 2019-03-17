package springbooktest.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by adaeng on 17/03/2019.
 */
public class UpperCaseHandler implements InvocationHandler {
    private Object target;

    public UpperCaseHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target,args); //타켓으로 위임 인터페이스의 메소드 호출에 모두 적용됨

        if(ret instanceof String){//리턴타입과 메소드 이름일치 하는 경우에 부가기능 넣고 싶을 경우(method.getName().startsWith("say")
            return ((String)ret).toUpperCase();
        }else {
            return ret;
        }
    }
}
