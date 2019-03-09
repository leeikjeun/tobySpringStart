package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 9..
 */
public class GetUserStatementStrategy implements StatementStrategy {
    private Object object;

    public GetUserStatementStrategy(Object object){
        this.object = object;
    }

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        String id = (String) object;
        PreparedStatement ps = c.prepareStatement("SELECT id, name, password FROM users WHERE id = ?");
        ps.setString(1,id);
        return ps;
    }
}
