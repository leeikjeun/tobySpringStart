package springbook.user.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adaeng on 15/03/2019.
 * 목 오브젝트
 */
public class DumyMailSender implements MailSender {

    private String host;
    private List<String> requests = new ArrayList<>();

    public DumyMailSender(){
        requests = new ArrayList<>();
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        requests.add(simpleMailMessage.getTo()[0]); //첫번째 수식만 저장
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

    }

    public List<String> getRequests() {
        return requests;
    }
}
