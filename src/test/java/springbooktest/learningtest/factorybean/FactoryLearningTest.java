package springbooktest.learningtest.factorybean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.factorybean.Message;
import springbook.user.factorybean.MessageFactoryBean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by adaeng on 18/03/2019.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/learningAC.xml")
public class FactoryLearningTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean(){
        Message message = context.getBean("message",Message.class);
        assertThat(message, is(Message.class));
        assertThat(message.getText() ,is("Factory Bean"));
    }

}
