package springbooktest.daoTest;

/**
 * Created by adaeng on 2019. 3. 7..
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserDaoTest {

    UserDaoJdbc userDao;
    ApplicationContext context;

    @Before
    public void setting(){
//        context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        userDao = context.getBean("userDao", UserDaoJdbc.class);
        context = new GenericXmlApplicationContext("applicationContext.xml");
        userDao = context.getBean("userDao",UserDaoJdbc.class);
    }

    @Test
    public void userGetTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);


        userDao.add(user);
        User checkUser = userDao.get("testId123");

        assertThat(user.getId(),is(checkUser.getId()));
        assertThat(user.getName(),is(checkUser.getName()));
        assertThat(user.getPassword(),is(checkUser.getPassword()));
    }

    private User getUser(String id, String name, String pass, Level level, int login, int recommend) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(pass);
        user.setLevel(level);
        user.setLogin(login);
        user.setRecommend(recommend);
        return user;
    }

    @Test
    public void userAddTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);

        userDao.add(user);
    }

    @Test
    public void getCountTest() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);

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

        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);

        User user2 = getUser("testId1234", "testName1", "testPass2", Level.SILVER, 20, 100);

        userDao.add(user);
        userDao.add(user2);

        userDao.deleteUser("testId123");

        int count = userDao.getCount();

        assertThat(1,is(1));
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);
        User user2 = getUser("testId1234", "testName1", "testPass2", Level.SILVER, 20, 100);


        userDao.add(user);
        userDao.add(user2);

        List<User> list = userDao.getAll();

        assertThat(user.getId(),is(list.get(0).getId()));
        assertThat(user.getName(),is(list.get(0).getName()));
        assertThat(user.getPassword(),is(list.get(0).getPassword()));

        assertThat(user2.getId(),is(list.get(1).getId()));
        assertThat(user2.getName(),is(list.get(1).getName()));
        assertThat(user2.getPassword(),is(list.get(1).getPassword()));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        User user = getUser("testId123","testName","testPass",Level.BASIC,1,0);

        userDao.add(user);

        user.setName("test123");
        user.setPassword("testpa123");

        userDao.update(user);

        User test = userDao.get(user.getId());

        assertThat(test.getName(),is(user.getName()));
        assertThat(test.getPassword(),is(user.getPassword()));
    }


}
