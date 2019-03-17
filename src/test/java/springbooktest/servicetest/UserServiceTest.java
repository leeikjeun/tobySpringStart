package springbooktest.servicetest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.MockUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.TestUserService;
import springbook.user.service.UserBasicUPgradePolicy;
import springbook.user.service.UserSerivceImpl;
import springbook.user.service.UserServiceTx;
import springbook.user.service.mail.DumyMailSender;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static springbook.user.service.UserBasicUPgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserBasicUPgradePolicy.MIN_RECCOMEND_FOR_GOLD;

/**
 * Created by adaeng on 11/03/2019.
 *  @RunWith 할때 처음에 에러가 났으나 에러 이유는 Junit 버전이 낮아 지원을 안하는 것이였음
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserServiceTx userServiceTx;

    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void userSetting(){
        users = Arrays.asList(
                new User("bumjin","bak","p1", Level.BASIC,MIN_LOGCOUNT_FOR_SILVER-1,0),
                new User("joytouch","jo","p2", Level.BASIC,MIN_LOGCOUNT_FOR_SILVER,0),
                new User("erwins","sin","p3", Level.SILVER,60,MIN_RECCOMEND_FOR_GOLD-1),
                new User("madnite1","lee","p4", Level.SILVER,60,MIN_RECCOMEND_FOR_GOLD),
                new User("green","oh","p5", Level.GOLD,100,100)
        );


    }



    // 업그레디드 된 경우를 테스트하려는 것인지 쉽게 파악이 안됨
    // 좀더 이해하기 쉽게 true false로 변경
    @Test
    public void levelUpdateTest() throws SQLException {
        UserSerivceImpl userSerivceImpl = new UserSerivceImpl();

        MockUserDao userDao = new MockUserDao(this.users);
        userSerivceImpl.setUserDao(userDao);

        DumyMailSender dumyMailSender = new DumyMailSender();
        userSerivceImpl.setMailSender(dumyMailSender);

        UserBasicUPgradePolicy userBasicUPgradePolicy = new UserBasicUPgradePolicy();
        userBasicUPgradePolicy.setUserDao(userDao);
        userSerivceImpl.setUserLevelUpgradePolicy(userBasicUPgradePolicy);

        userSerivceImpl.upgradeLevels();

        List<User> updated = userDao.getUpdated();
        assertThat(updated.size(), is(2));

        checkUserLevel(updated.get(0),"joytouch",Level.SILVER);
        checkUserLevel(updated.get(1),"madnite1",Level.GOLD);
    }

    private void checkUserLevel(User user, String expectId, Level expectLevel) {
        assertThat(user.getId(),is(expectId));
        assertThat(user.getLevel(),is(expectLevel));
    }

    @Test
    public void userServiceAdd(){
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithOutLevel = users.get(0);
        userWithOutLevel.setLevel(null);

        userServiceTx.add(userWithLevel);
        userServiceTx.add(userWithOutLevel);


        User loadUserWithLevel = userDao.get(userWithLevel.getId());
        User loadUserWithoutLevel = userDao.get(userWithOutLevel.getId());

        assertThat(loadUserWithLevel.getLevel(), is(userWithLevel.getLevel()));
        assertThat(loadUserWithoutLevel.getLevel(),is(userWithOutLevel.getLevel()));
    }

    @Test
    public void upgradeAllOrNothing(){
        userDao.deleteAll();
        for(User user : users)
            userDao.add(user);

        try {
//            fail("TestUserServiceException expected");
        }catch (Exception e){

        }
    }

}
