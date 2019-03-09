package springbook.user.dao;

import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by adaeng on 2019. 3. 7..
 * 사용자 정보를 DB에 넣고 관리할 수 있는 DAO 클래스
 * 개발자의 코드는 미래의 변화에 어떻게 대비할 것인가?
 *
 *
 */

public class UserDao {

    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
        jdbcContextWithStatementStrategy(strategy);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        User user = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();


            StatementStrategy statementStrategy = new GetUserStatementStrategy(id);
            ps = statementStrategy.makeStatement(c);

            rs = ps.executeQuery();
            rs.next();

            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    public int getCount(){
        int count = 0;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            StatementStrategy statementStratygy = new GetCountStatementStrategy();
            ps = statementStratygy.makeStatement(c);

            rs = ps.executeQuery();
            rs.next();

            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    public void deleteUser(String id){
        StatementStrategy st = connetion -> {
            PreparedStatement ps = connetion.prepareStatement("DELETE from users where id = ?");
            ps.setString(1,id);
            return ps;
        };

        jdbcContextWithStatementStrategy(st);
    }

    public void deleteAll(){
        StatementStrategy strategy = connetion -> {
            PreparedStatement ps = connetion.prepareStatement("DELETE FROM users");
            return ps;
        };
        jdbcContextWithStatementStrategy(strategy);
    }

    private void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = statementStrategy.makeStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
