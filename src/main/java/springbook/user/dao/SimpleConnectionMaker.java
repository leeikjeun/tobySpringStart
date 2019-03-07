package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 7..
 * 관심사에 따라 분리 하였으나
 * 문제가 벤더사마다 다른 UserDao를 사용한다는 문제 발생
 */
public class SimpleConnectionMaker {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/spring", "root", "as0109247"
        );
    }
}
