package springbook.user.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by adaeng on 15/03/2019.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    private MailSender mailSender;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void upgradeLevels() throws SQLException {

        List<User> users = userDao.getAll();
        for(User user : users){
            if(userLevelUpgradePolicy.canUpgradeUser(user)){
                userLevelUpgradePolicy.upgradeLevel(user);
                sendUpgradeEmail(user);
            }
        }
    }


    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getId());
        mailMessage.setFrom("useradmin@ksung.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자 등급 업그레이드 됨");

        mailSender.send(mailMessage);
    }

    /**
     * Created by adaeng on 18/03/2019.
     */
    public static class UserTestUpGradePolicy implements UserLevelUpgradePolicy{

        @Override
        public boolean canUpgradeUser(User user) {
            return false;
        }

        @Override
        public void upgradeLevel(User user) {

        }
    }
}
