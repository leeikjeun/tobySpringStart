package springbook.user.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by adaeng on 11/03/2019.
 * 현재 밑의 트랜잭션 경계설정 코드는 기술과 환경에 종속된다.
 * 만약 여러개의 DB에 데이터를 넣은 작업을 해야한다면 한개 이상의 DB로의 작업을 하나의 트랜잭션으로 만드는건 불가능
 * 왜냐하면 JDBC의 Connection을 이용한 트랜잭션 방식은 로컬 트랜잭션이기 때문에
 * 로컬 트랜잭션은 하나의 DB 케넥션에 종속된다
 * 따라서 각 DB에 커넥션을 통해 만드는 것이 아니라 트랜잭션 관리자를 통해 글로벌 트랜잭션방식을 사용해야 한다.
 * 자바에서 제공하는 API JTA
 */
public class UserService {

    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() throws SQLException, NamingException {
        InitialContext ctx = new InitialContext();
        UserTransaction tx = (UserTransaction)ctx.lookup("USER_TX_JNDI_NAME");

        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for(User user : users){
                if(userLevelUpgradePolicy.canUpgradeUser(user)){
                    userLevelUpgradePolicy.upgradeLevel(user);
                }
            }
            c.commit();
        } catch (Exception e) {
            c.rollback();
            throw e;
        } finally {
            //안전하게 DB커넥션을 닫는 과정
            DataSourceUtils.releaseConnection(c,dataSource);
            //동기화 제거 작업
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
