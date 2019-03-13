package springbook.user.service;

import springbook.user.domain.Level;
import springbook.user.domain.User;
import static springbook.user.service.UserBasicUPgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserBasicUPgradePolicy.MIN_RECCOMEND_FOR_GOLD;


/**
 * Created by adaeng on 13/03/2019.
 */
public class UserTestUpgradePolicy extends UserBasicUPgradePolicy implements UserLevelUpgradePolicy {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean canUpgradeUser(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER:
                return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
            case GOLD: return false;
            default: throw new IllegalArgumentException("unknow Level " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        if(user.getId().equals(this.id)) throw new TestUserServiceException();
        super.upgradeLevel(user);
    }

    static class TestUserServiceException extends RuntimeException{
    }
}


