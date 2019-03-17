package springbook.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.domain.User;

import java.sql.SQLException;

/**
 * Created by adaeng on 15/03/2019.
 * 이와 같은 프록시 코드가 불편한 이유
 * 1. 타켓의 인터페이스를 구현하고 위임하는 코드를 작성하기가 번거롭다.
 * 2. 부가기능 코드가 중복될 가능성이 많다.(만약 add 메소드에도 추가 기능을 넣는다면 똑같은 코드를 넣어줘야함
 */
public class UserServiceTx implements UserService {

    private UserService userService;//타켓 오브젝트
    private PlatformTransactionManager transactionManager;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void upgradeLevels() throws SQLException {//추가 기능
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            userService.upgradeLevels(); // 위임

            this.transactionManager.commit(status);
        }catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void add(User user) {
        this.userService.add(user);
    }//위임
}
