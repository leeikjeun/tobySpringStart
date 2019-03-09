package springbook.user.dao;

import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.jar.JarEntry;

/**
 * Created by adaeng on 2019. 3. 7..
 * 사용자 정보를 DB에 넣고 관리할 수 있는 DAO 클래스
 * 개발자의 코드는 미래의 변화에 어떻게 대비할 것인가?
 *
 *
 */

public class UserDao {

    JdbcContext jdbcContext;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    // 변하는것과 변하지 않는 것
    public void add(User user) throws ClassNotFoundException, SQLException {
//        insert into users(id, name, password) VALUES (?,?,?)
        StatementStrategy strategy = c -> {
            PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) VALUES (?,?,?)");
            ps.setString(1,user.getId());
            ps.setString(2,user.getName());
            ps.setString(3,user.getPassword());
            return ps;
        };
        jdbcContext.jdbcContextWithStatementStrategy(strategy);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        StatementStrategy strategy = c -> {
            PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
            ps.setString(1,id);
            return ps;
        };

        return jdbcContext.getUserJdbcStatement(strategy);
    }

    public int getCount(){
        StatementStrategy st = c -> {
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM users");
            return ps;
        };

        return jdbcContext.getCountJdbcStatement(st);
    }

    public void deleteUser(String id){
        StatementStrategy st = connetion -> {
            PreparedStatement ps = connetion.prepareStatement("DELETE from users where id = ?");
            ps.setString(1,id);
            return ps;
        };

        jdbcContext.jdbcContextWithStatementStrategy(st);
    }

    public void deleteAll(){
        StatementStrategy strategy = connetion -> {
            PreparedStatement ps = connetion.prepareStatement("DELETE FROM users");
            return ps;
        };
        jdbcContext.jdbcContextWithStatementStrategy(strategy);
    }
}
