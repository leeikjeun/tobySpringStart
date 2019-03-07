package springbooktest.daoTest;

/**
 * Created by adaeng on 2019. 3. 7..
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    UserDao userDao;

    @Before
    public void setting(){
        userDao = new UserDao();
    }

    @Test
    public void userGetTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        userDao.add(user);
    }

    @Test
    public void userAddTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        User checkUser = userDao.get("testId123");

        assertThat(user.getId(),is(checkUser.getId()));
        assertThat(user.getName(),is(checkUser.getName()));
        assertThat(user.getPassword(),is(checkUser.getPassword()));

    }





}
