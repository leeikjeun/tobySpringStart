package springbook.user.service.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

/**
 * Created by adaeng on 18/03/2019.
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
