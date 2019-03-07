package springbook.user.domain;

/**
 * Created by adaeng on 2019. 3. 7..
 */


public class User {
    //사용자 정보를 저장할 때는 자바빈 규약을 따르는 오브젝트를 이용하면 편리

    /*
    * 자바 빈이란
    * 원래 비주얼 툴에서 조작 가능한 컴포넌트를 말함
    * 자바의 주력 개발 플렛폼이 웹 기반의 엔터프라이즈 방식으로 바뀌면서 비주얼 컴포넌트로서 자바빈은 인기를 잃음
    * 이제는 자바빈이라고 말하면 비주얼 컴포넌트를 말하는것이 아니라 두 가지 관례에 따른 오브젝트를 말함
    * 1. 디폴트 생선자 : 파라미터가 없는 디폴트 생성자를 갖고 있어야 함
    * 2. 프로퍼티 : 자바빈이 노출하는 이름을 가진 속성을 포로퍼티라고 한다(프로퍼티는 get set으로 접근함
    * */

    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
