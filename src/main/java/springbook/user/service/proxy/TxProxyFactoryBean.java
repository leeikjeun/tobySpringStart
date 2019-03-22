package springbook.user.service.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

/**
 * Created by adaeng on 18/03/2019.
 * 프록시 팩토리 빈의 한계
 * 1. 부가기능이 타켓 오브젝트마다 새로 만들어지는 문제는 스프링의 ProxyFactoryBean의 어드바이저를 통해 해결
 * 2. 부가기능의 적용이 필요한 타킷 오브젝트마다 거의 비슷한 내용의 빈 설정정보를 추가해주는 부분이 문제이닷
 */
public class TxProxyFactoryBean implements FactoryBean<Object> {
    private Object target;
    private PlatformTransactionManager manager;
    private String pattern;
    private Class<?> serviceInterface; //다이나믹 프록시를 생성할 때 마다 필요하다

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTransactionManager(manager);
        txHandler.setPattern(pattern);
        txHandler.setTarget(target);

        return Proxy.newProxyInstance(
                getClass().getClassLoader(),new Class[] {serviceInterface}, txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
