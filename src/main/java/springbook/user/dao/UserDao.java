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

/*
    관계설정 책임의 분리
    1.인터페이스를 통하여 UserDao와 ConnectionMaker를 분리하였는데도 인터페이스뿐만 아니라 구체적인 구현 클래스를 알아야함
    2.UserDao 변경 없이는 DB 커넥션 기능의 확장이 자유롭지 못함

    따라서 구현 클래스에 대한 책임을 밖으로 돌림림
    책임을 넘김으로써 UserDao는 데이터 엑세스 작업을 위해 SQL을 생성하고 이를 실행하는 데만 집중
 */
public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker){
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
