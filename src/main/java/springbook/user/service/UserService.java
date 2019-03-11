package springbook.user.service;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

/**
 * Created by adaeng on 11/03/2019.
 */
public class UserService {
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    /*
    *  현재 upgradeLevels의 문제점
    *  1. if/elseif/else로 인해일기 불편
    *  2. 레벨 변화단계와 업그레이드 조건 조건이 충족됐을 때 해야할 작업들이 섞여있다.
    *  3. 플래그를 두고 이를 변경하고 마지막에 이를 확인해서 업데이트를 진행이 깔끔하지 않음
    * */
    public void upgradeLevels(){
        List<User> users = userDao.getAll();
        for(User user : users){
            if(canUpgradeUser(user)){
                upgradeLevel(user);
            }
        }

    }

    private void upgradeLevel(User user) {
        Level nextLevel = user.getLevel().getNextLevel();
        if(nextLevel == null){
            throw new IllegalStateException(nextLevel + "은 레벨업이 불가능 합니다");
        }
        user.setLevel(nextLevel);
        userDao.update(user);
    }

    private boolean canUpgradeUser(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC: return user.getLogin() >= 50;
            case SILVER: return user.getRecommend() >= 30;
            case GOLD: return false;
            default: throw new IllegalArgumentException("unknow Level " + currentLevel);
        }
    }


    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
