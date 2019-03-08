package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by adaeng on 2019. 3. 7..
 * 스프링에서 제공하는 IoC를 사용
 *
 */

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }

}
