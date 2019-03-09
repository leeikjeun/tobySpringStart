package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

/**
 * Created by adaeng on 2019. 3. 7..
 * 사용자 정보를 DB에 넣고 관리할 수 있는 DAO 클래스
 * 개발자의 코드는 미래의 변화에 어떻게 대비할 것인가?
 *  --> 분리 확장형 설계
 *  기초 관심사 분리
 */

public class UserDao {

    private ConnectionMaker connectionMaker;

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) VALUES (?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT id, name, password FROM users WHERE id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

}
