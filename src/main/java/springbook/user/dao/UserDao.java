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

/*현재 userDao 관심 사항
* 1. DB와 연결을 위한 커넥션을 어떻게 가져올까?(DB 쓰고, 어떤 드라이버를 사용?, 어떤 로그인 정보, 커넥션을 생성 방법 등)
* 2. DB에 보낼 SQL 문장을 담을 Stetement를 만들고 실행하는 것
* 3. 리소스 리턴(connect, statement) --> 공용 리소스 왜(DB를 이 곳 한 클래스에서만 사용하지 않기 때문!!)
* */

public class UserDao {

    /*
       관심사에 따라서 분리한 오브젝트는 제각기 독특한 변화의 특징이 있다
       1. 데이터 엑세스 로직을 어떻게 만들 것인가 --> 어떤 테이블? 어떤 필드 이름??
       2. DB연결을 어떤 방법으로 할 것인가 --> JDBC API사용 or DB전용 API 사용?

       관심사가 다른 두가지 코드를 좀더 화끈하게 분리!!
     */

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDao(){
        simpleConnectionMaker = new SimpleConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = simpleConnectionMaker.getConnection();

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
        Connection c = simpleConnectionMaker.getConnection();

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
