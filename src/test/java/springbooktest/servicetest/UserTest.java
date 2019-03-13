package springbooktest.servicetest;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.nio.channels.Pipe;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by adaeng on 13/03/2019.
 */
public class UserTest {
    User user;

    @Before
    public void setUp(){
        user = new User();
    }


    @Test
    public void upgrradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.getNextLevel() == null)
                continue;

            user.setLevel(level);
            user.upgradeLevel();

            assertThat(user.getLevel(), is(level.getNextLevel()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.getNextLevel() == null)
                continue;

            user.setLevel(level);
            user.upgradeLevel();
        }
    }

}
