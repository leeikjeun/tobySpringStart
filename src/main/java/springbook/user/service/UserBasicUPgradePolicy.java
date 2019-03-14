package springbook.user.service;

import org.springframework.mail.MailSender;
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        Properties props = new Properties();
        props.put("mail.smtp.host","mail.ksug.org");
        Session s = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(s);

        try {
            message.setFrom(new InternetAddress("useradmin@ksung.org"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getId()));
            message.setSubject("Upgrade 안내");
            message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
