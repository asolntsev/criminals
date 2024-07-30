package ui;

import com.codeborne.selenide.Configuration;
import criminals.Application;
import org.junit.jupiter.api.BeforeEach;
import play.Play;

import static com.codeborne.selenide.TextCheck.FULL_TEXT;


public class BaseUITest {
  @BeforeEach
  public final void startAUT() {
    if (!Play.started) {
      new Application().start("test");

      Configuration.baseUrl = "http://localhost:9000";
      Configuration.browserSize = "1024x800";
      Configuration.browser = "chrome";
      Configuration.textCheck = FULL_TEXT;
    }
  }
}
