package ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends BaseUITest {
  private final MailServerEmulator mailServer = new MailServerEmulator();

  @Test
  public void loginWithOtpCode() {
    open("/");
    $("[name=username]").val("admin@mail.ee");
    $("[name=password]").val("admin@mail.ee").pressEnter();
    $("[name=otpCode]").should(appear);

    String firstEmail = mailServer.getMessages().get(0);
    String otpCode = firstEmail.replaceFirst("Хочешь залогиниться\\? Введи этот код: (.*)", "$1");

    $("[name=otpCode]").val(otpCode).pressEnter();
    $("h1").shouldHave(text("Hello, admin@mail.ee!"));
  }


  @Before
  public void setUp() {
    mailServer.start();
  }

  @After
  public void tearDown() {
    mailServer.shutdown();
  }
}
