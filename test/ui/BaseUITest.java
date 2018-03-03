package ui;

import com.codeborne.selenide.Configuration;
import org.junit.Before;
import play.Play;
import play.server.Server;

public class BaseUITest {
  @Before
  public final void startAUT() throws InterruptedException {
    if (!Play.started) {
      Thread playStarter = new Thread(new PlayStarter(), "Play! starter thread");

      playStarter.start();
      playStarter.join();

      Configuration.startMaximized = false;
      Configuration.browserSize = "1024x800";
      Configuration.browser = "chrome";
      //    Configuration.headless = true;
    }
  }

  private static class PlayStarter implements Runnable {
    @Override
    public void run() {
      Play play = new Play();
      play.init("test");
      play.start();
      new Server(9000).start();

      Configuration.baseUrl = "http://localhost:9000";
      Play.configuration.setProperty("application.baseUrl", Configuration.baseUrl);
    }
  }
}
