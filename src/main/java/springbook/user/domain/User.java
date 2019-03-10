package springbook.user.domain;

/**
 * Created by adaeng on 2019. 3. 7..
 */


public class User {

    // 만약에 level이라는 서비스를 제공하기 위해 User에 레벨값을 넣을수 있으나 이경우에는
    // 잘못된 레벨값이 들어가 문제를 이를킬수 있다
    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;

    int level;

    public void setLevel(int level) {
        this.level = level;
    }

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
