package ui;

import com.codeborne.selenide.Configuration;
import criminals.Application;
import org.junit.Before;
import play.Play;


public class BaseUITest {
  @Before
  public final void startAUT() {
    if (!Play.started) {
      new Application().start("test");

      Configuration.baseUrl = "http://0.0.0.0:9000";
      Configuration.browserSize = "1024x800";
      Configuration.browser = "chrome";
    }
  }
}
