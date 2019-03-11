package springbooktest.servicetest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.service.UserService;

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

    @Test
    public void levelUpdateTest(){
        assertThat(this.userService, is(notNullValue()));
    }


}
