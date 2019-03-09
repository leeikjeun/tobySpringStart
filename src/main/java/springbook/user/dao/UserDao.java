package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 변하는것과 변하지 않는 것
    public void add(User user) throws ClassNotFoundException, SQLException {
        String sql = "insert into users(id, name, password) VALUES (?,?,?)";
        Object[] parms = new Object[]{user.getId(),user.getName(),user.getPassword()};

        this.jdbcTemplate.update(sql,parms);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        String sql = "SELECT id, name, password FROM users WHERE id = ?";
        Object[] parms = new Object[]{id};
        User user1 = null;
        try {
            user1 = jdbcTemplate.queryForObject(sql,parms,(resultset, i) -> {
               User user = new User();
               user.setId(resultset.getString("id"));
               user.setName(resultset.getString("name"));
               user.setPassword(resultset.getString("password"));
               return user;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user1;
    }

    public int getCount(){
        String sql = "SELECT COUNT(*) FROM users";

        int count = jdbcTemplate.queryForObject(sql,new Object[0],(resultset,i) -> {
            int count1 = resultset.getInt(1);
            return count1;
        });

        return count;
    }

    public void deleteUser(String id){
        String sql = "delete from users where id = ?";
        Object[] parms = new Object[]{id};

        jdbcTemplate.update(sql,parms);
    }

    public void deleteAll(){
        String sql = "delete from users";

        jdbcTemplate.update(sql);
    }
}
