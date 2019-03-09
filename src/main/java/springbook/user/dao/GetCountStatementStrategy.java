package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 9..
 */
public class GetCountStatementStrategy implements StatementStrategy {

    @Override
    public PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM users");
        return ps;
    }
}
