package services;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;

import javax.inject.Singleton;

import static java.util.concurrent.TimeUnit.SECONDS;

@Singleton
public class OtpCodeService {
  public String generateOtpLabel() {
    return "ABC";
  }

  public String generateOtpCode() {
    return String.valueOf(System.nanoTime()).substring(10, 14);
  }

  public void sendOtpCode(String username, String otpCode) throws Exception {
    Email mail = new SimpleEmail();
    mail.setCharset("UTF-8");
    //mail.setHostName(config.domain());
    //String domain = config.domain();
    mail.setFrom("login@superservice.com");
    mail.setMsg("Хочешь залогиниться? Введи этот код: " + otpCode);
    mail.setSubject("Логин в суперсервис");
    mail.addTo(username + "@superservice.com");
    Mail.send(mail);
  }
}
