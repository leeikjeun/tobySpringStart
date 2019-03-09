package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by adaeng on 2019. 3. 7..
 * 스프링에서 제공하는 IoC를 사용
 * 애플리케이션 컨텍스트는 싱글톤을 저장하고 관리하는 싱글톤 레지스토리이다
 * 싱글톤을 사용하는 이유는 스프링이 주로 자바 엔터프라이즈 기술을 사용하는 서버환경이기 떄문이다(여러 요청에도 한가지의 오브젝트만 사용하기위해)
 *
 */

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }

}
