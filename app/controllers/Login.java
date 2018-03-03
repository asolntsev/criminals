package controllers;

import play.mvc.results.Redirect;
import play.mvc.results.Result;
import play.rebel.RebelController;
import play.rebel.View;
import services.OtpCodeService;

import javax.inject.Inject;

public class Login extends RebelController {
  @Inject
  OtpCodeService otpCodeService;

  public Result loginForm() {
    return new View("login/form.html");
  }

  public Result firstStep(String username, String password) throws Exception {
    if (!username.equals(password)) {
      return new Redirect("/");
    }

    String otpLabel = otpCodeService.generateOtpLabel();
    String otpCode = otpCodeService.generateOtpCode();
    otpCodeService.sendOtpCode(username, otpCode);

    session.put("login.username", username);
    session.put("login.otpLabel", otpLabel);
    session.put("login.otpCode", otpCode);

    return new View("login/otpCodeForm.html").with("otpLabel", otpLabel);
  }

  public Result secondStep(String otpCode) {
    if (!otpCode.equals(session.get("login.otpCode"))) {
      return new View("login/otpCodeForm.html").with("otpLabel", session.get("login.otpLabel"));
    }

    session.put("username", session.get("login.username"));
    session.remove("login.username");
    return new Redirect("/dashboard");
  }
}
