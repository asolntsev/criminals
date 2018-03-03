import com.google.inject.AbstractModule;
import play.Play;

import static com.google.inject.name.Names.named;

public class Module extends AbstractModule {
  @Override
  protected void configure() {
    bind(String.class).annotatedWith(named("criminal-records.service.url"))
        .toInstance(Play.configuration.getProperty("criminal-records.service.url"));
  }
}
