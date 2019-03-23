package springbook.user.service.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by adaeng on 19/03/2019.
 * TransactionDefinition는 DefaultTransactionDefinition의 인터페이스다
 * TransactionDefinition는 4가지 속성을 정의함
 * 1.전파
 * 2.격리수준
 * 3.제한시간
 * 4.읽기 전용
 */
public class TransactionAdvice implements MethodInterceptor {
    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = this.transactionManager.getTransaction(
                new DefaultTransactionDefinition()
        );

        try {
            Object ret = invocation.proceed();
            this.transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }
}
