package springbook.user.service;

import springbook.user.domain.User;

/**
 * Created by adaeng on 13/03/2019.
 */
public interface UserLevelUpgradePolicy {
    public boolean canUpgradeUser(User user);
    public void upgradeLevel(User user);
}
