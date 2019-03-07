package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by adaeng on 2019. 3. 7..
 */
public interface ConnectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}
