package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

/**
 * Created by adaeng on 2019. 3. 7..
 * 사용자 정보를 DB에 넣고 관리할 수 있는 DAO 클래스
 * 개발자의 코드는 미래의 변화에 어떻게 대비할 것인가?
 * 예외처리 처리할 때 반드시 지켜야 할 핵심 원칙
 * 모든 예외는 적절하게 복구되는지 아니면 작업을 중단시키고 운영자 또는 개발자에게 분명하게 통보돼야 한다
 */

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 변하는것과 변하지 않는 것
    public void add(User user){
        String sql = "insert into users(id, name, password,level,login,recommend) VALUES (?,?,?,?,?,?)";
        Object[] parms = new Object[]{user.getId(),user.getName(),user.getPassword(),user.getLevel().intValue(),user.getLogin(),user.getRecommend()};

        this.jdbcTemplate.update(sql,parms);
    }

    public User get(String id){

        String sql = "SELECT * FROM users WHERE id = ?";
        Object[] parms = new Object[]{id};
        User user1 = null;
        try {
            user1 = jdbcTemplate.queryForObject(sql,parms,(resultset, i) -> {
               User user = new User();
               user.setId(resultset.getString("id"));
               user.setName(resultset.getString("name"));
               user.setPassword(resultset.getString("password"));
               user.setLevel(Level.valueOf(resultset.getInt("level")));
               user.setLogin(resultset.getInt("login"));
               user.setRecommend(resultset.getInt("recommend"));
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

    public List<User> getAll() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql,(resultSet,i) ->{
           User user = new User();
           user.setId(resultSet.getString("id"));
           user.setName(resultSet.getString("name"));
           user.setPassword(resultSet.getString("password"));
           user.setLevel(Level.valueOf(resultSet.getInt("level")));
           user.setLogin(resultSet.getInt("login"));
           user.setRecommend(resultSet.getInt("recommend"));
           return user;
        });
    }


    public void update(User user) {
//        UPDATE tablename SET filedA='456' WHERE test='123' LIMIT 10;

        String sql = "UPDATE users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?";
        Object[] parms = new Object[]{user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(),user.getRecommend(), user.getId()};

        jdbcTemplate.update(sql, parms);
    }
}
