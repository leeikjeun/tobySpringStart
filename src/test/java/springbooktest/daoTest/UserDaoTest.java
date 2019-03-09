package springbooktest.daoTest;

/**
 * Created by adaeng on 2019. 3. 7..
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    UserDao userDao;
    ApplicationContext context;

    @Before
    public void setting(){
//        context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        userDao = context.getBean("userDao", UserDao.class);
        context = new GenericXmlApplicationContext("applicationContext.xml");
        userDao = context.getBean("userDao",UserDao.class);
    }

    @Test
    public void userGetTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");
        userDao.add(user);
        User checkUser = userDao.get("testId123");

        assertThat(user.getId(),is(checkUser.getId()));
        assertThat(user.getName(),is(checkUser.getName()));
        assertThat(user.getPassword(),is(checkUser.getPassword()));
    }

    @Test
    public void userAddTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        userDao.add(user);
    }

    @Test
    public void getCountTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        User user2 = new User();
        user2.setId("testId1234");
        user2.setName("testName1");
        user2.setPassword("testPass2");

        userDao.add(user);
        userDao.add(user2);

        int count = userDao.getCount();

        assertThat(2,is(count));
    }

    @Test
    public void deleteUser() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        User user = new User();
        user.setId("testId123");
        user.setName("testName");
        user.setPassword("testPass");

        User user2 = new User();
        user2.setId("testId1234");
        user2.setName("testName1");
        user2.setPassword("testPass2");

        userDao.add(user);
        userDao.add(user2);

        userDao.deleteUser("testId123");

        int count = userDao.getCount();

        assertThat(1,is(1));
    }



}
