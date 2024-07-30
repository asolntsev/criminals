package controllers;

import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.results.Redirect;
import play.mvc.results.Result;
import play.rebel.View;
import services.LoginService;
import services.OtpCodeService;

import javax.inject.Inject;
import java.util.Optional;

public class Login extends Controller {
  private static final Logger log = LoggerFactory.getLogger(Login.class);

  @Inject LoginService users;
  @Inject OtpCodeService otpCodeService;

  public Result loginForm() {
    return new View("login/form.html")
      .with("username", "john")
      .with("password", "secret");
  }

  public Result firstStep(@Required String username, @Required String password) throws Exception {
    if (Validation.hasErrors()) {
      return new View("login/form.html")
          .with("username", username)
          .with("password", password);
    }

    Optional<User> user = users.login(username, password);

    if (user.isEmpty()) {
      return new View("login/form.html")
          .with("username", username)
          .with("wrongCredentials", true);
    }
    log.info("Logging in as {} ...", username);

    String otpLabel = otpCodeService.generateOtpLabel();
    String otpCode = otpCodeService.generateOtpCode();
    String sentTo = otpCodeService.sendOtpCode(user.get(), otpCode);

    session.put("login.username", username);
    session.put("login.otpLabel", otpLabel);
    session.put("login.otpCode", otpCode);
    session.put("login.sentTo", sentTo);

    log.info("Generated OTP code {} for {}, label: {}", otpCode, username, otpLabel);
    return otpCodeForm(user.get(), otpLabel, sentTo);
  }

  public Result secondStep(String otpCode) {
    String username = session.get("login.username");
    User user = users.byUsername(username);
    if (!otpCode.equals(session.get("login.otpCode"))) {
      log.info("Wrong otp code");
      return otpCodeForm(user, session.get("login.otpLabel"), session.get("login.sentTo"));
    }

    session.put("user.name", user.fullName());
    session.remove("login.username");

    log.info("Logged in as {}", session.get("user.name"));
    return new Redirect("/dashboard");
  }

  private View otpCodeForm(User user, String otpLabel, String sentTo) {
    return new View("login/otpCodeForm.html")
      .with("name", user.fullName())
      .with("otpLabel", otpLabel)
      .with("sentTo", sentTo);
  }
}
