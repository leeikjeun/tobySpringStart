package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 7..
 * 템플릿 메소드 패턴을 이용하여 Connection 타입을 결정함
 *
 */
public class NUserDao extends UserDao{

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/spring", "root", "as0109247"
        );
    }

}
