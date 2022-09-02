package ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import play.i18n.Messages;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends BaseUITest {
  private final MailServerEmulator mailServer = new MailServerEmulator();

  private final String usernameFieldName = "username";
  private final String passwordFieldName = "password";

  @Test
  public void loginWithoutUsernameAndPassword() {
    open("/");
    $("[name=" + passwordFieldName + "]").pressEnter();

    fieldShouldHaveAWarning(usernameFieldName, "validation.required");
    fieldShouldHaveAWarning(passwordFieldName, "validation.required");
  }

  @Test
  public void loginWithUsernameWhichDoesNotResembleAnEmailAddress() {
    open("/");
    $("[name=" + usernameFieldName + "]").val("admin");
    $("[name=" + passwordFieldName + "]").val("admin").pressEnter();

    fieldShouldHaveAWarning(usernameFieldName, "validation.email");
  }

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

  private void fieldShouldHaveAWarning (final String fieldName, final String messageKey) {
    $("[name=" + fieldName + "] + .text-danger").shouldHave(
            text(Messages.get(messageKey, fieldName))
    );
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
