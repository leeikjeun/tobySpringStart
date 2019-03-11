package springbooktest.servicetest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by adaeng on 11/03/2019.
 *  @RunWith 할때 처음에 에러가 났으나 에러 이유는 Junit 버전이 낮아 지원을 안하는 것이였음
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void userSetting(){
        users = Arrays.asList(
                new User("bumjin","bak","p1", Level.BASIC,49,0),
                new User("joytouch","jo","p2", Level.BASIC,50,0),
                new User("erwins","sin","p3", Level.SILVER,60,29),
                new User("madnite1","lee","p4", Level.SILVER,60,30),
                new User("green","oh","p5", Level.GOLD,100,100)
        );
    }

    @Test
    public void levelUpdateTest(){
        userDao.deleteAll();

        for(User user : users)
            userDao.add(user);


        userService.upgradeLevels();

        checkLevel(users.get(0),Level.BASIC);
        checkLevel(users.get(1),Level.SILVER);
        checkLevel(users.get(2),Level.SILVER);
        checkLevel(users.get(3),Level.GOLD);
        checkLevel(users.get(4),Level.GOLD);

    }

    private void checkLevel(User user, Level level) {
        User updateUser = userDao.get(user.getId());
        assertThat(updateUser.getLevel(),is(level));
    }


}
