package springbook.user.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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
