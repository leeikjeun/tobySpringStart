package springbooktest.daoTest;

/**
 * Created by adaeng on 2019. 3. 7..
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    UserDao userDao;
    ApplicationContext context;

    @Before
    public void setting(){
        context = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = context.getBean("userDao", UserDao.class);
    }

    @Test
    public void userGetTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        User checkUser = userDao.get("testId123");

        assertThat(user.getId(),is(checkUser.getId()));
        assertThat(user.getName(),is(checkUser.getName()));
        assertThat(user.getPassword(),is(checkUser.getPassword()));
    }

    @Test
    public void userAddTest() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        userDao.add(user);

    }





}
