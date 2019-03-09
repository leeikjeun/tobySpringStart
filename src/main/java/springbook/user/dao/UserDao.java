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

    // 예외처리가 필요한 이유!!
    // 예외처리를 하지 않을때 미처 반환되지 못한 커넥션이 계속쌓이게 되면 어느 순간에 커넥션퓰에 여유가 없어지고 리소스가 모자란다는 심각한 오류가 발생할 수 있다
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement(
                    "insert into users(id, name, password) VALUES (?,?,?)"
            );
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

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

    public User get(String id) throws ClassNotFoundException, SQLException {
        User user = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement(
                    "SELECT id, name, password FROM users WHERE id = ?"
            );

            ps.setString(1, id);

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

            ps = c.prepareStatement(
                    "SELECT count(*) FROM users"
            );

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

    public void deleteAll(){
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement(
                    "DELETE from users"
            );
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
