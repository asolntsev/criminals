package ui;

import com.codeborne.selenide.Configuration;
import org.junit.Before;
import play.Play;
import play.server.Server;

public class BaseUITest {
  @Before
  public final void startAUT() throws InterruptedException {
    if (!Play.started) {
      Thread playStarter = new Thread(new ApplicationStarter(), "Play! starter thread");

      playStarter.start();
      playStarter.join();

      Configuration.baseUrl = "http://localhost:9000";
      Configuration.startMaximized = false;
      Configuration.browserSize = "1024x800";
      Configuration.browser = "chrome";
    }
  }

  private static class ApplicationStarter implements Runnable {
    @Override
    public void run() {
      Play play = new Play();
      play.init("test");
      play.start();
      new Server().start();
    }
  }
}
