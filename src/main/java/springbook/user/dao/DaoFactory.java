package springbook.user.dao;

/**
 * Created by adaeng on 2019. 3. 7..
 * 분리 기능을 담당할 클래스
 * 클래스의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 역할
 *
 */
public class DaoFactory {

    public UserDao userDao(){
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }

}
