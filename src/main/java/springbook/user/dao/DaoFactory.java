package springbook.user.dao;

/**
 * Created by adaeng on 2019. 3. 7..
 * 분리 기능을 담당할 클래스
 * 클래스의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 역할
 * DaoFactory의 책임
 *  - 애플리케이션의 오브젝트들을 구성하고 그 관계를 정의하는 책임(애플리케이션의 컴포넌트 역할을 하는 오브젝트와 구조결정 오브젝트 분리)
 *
 *
 */
public class DaoFactory {

    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }


    //이 오브젝트에 UserDao말고 다른 DAO가 있다고 가정할 때 이렇게 해야 코드의 중복 없음
    private ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }

}
