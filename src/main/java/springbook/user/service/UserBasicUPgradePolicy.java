package springbook.user.service;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

/**
 * Created by adaeng on 13/03/2019.
 */
public class UserBasicUPgradePolicy implements UserLevelUpgradePolicy {

    final public static int MIN_LOGCOUNT_FOR_SILVER = 50;
    final public static int MIN_RECCOMEND_FOR_GOLD = 30;

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
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
        user.upgradeLevel();
        userDao.update(user);
    }
}
