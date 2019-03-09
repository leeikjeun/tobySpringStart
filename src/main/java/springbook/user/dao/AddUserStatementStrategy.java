package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 9..
 */
public class AddUserStatementStrategy implements StatementStrategy {
    Object object;

    public AddUserStatementStrategy(Object object){
        this.object = object;
    }

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        User user = (User) object;
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) VALUES (?,?,?)");
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());
        return ps;
    }
}
