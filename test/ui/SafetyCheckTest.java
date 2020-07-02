package ui;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class SafetyCheckTest extends BaseUITest {
  @Rule
  public WireMockRule wireMockRule = new WireMockRule(9020);

  @Test
  public void isSafe_ifHasEmptyHistory() {
    stubFor(get(urlEqualTo("/criminal-records?ssn=2222222222"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/json")
            .withBody("[]")));

    open("/dashboard");
    $("[name=ssn]").val("2222222222").pressEnter();
    $("#result").shouldHave(text("Результат: криминальная история чиста. можно выпускать на волю."));
  }

  @Test
  public void noSafe_ifCriminalHasHistory() {
    stubFor(get(urlEqualTo("/criminal-records?ssn=1111111111"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/json")
            .withBody("[{\"description\":\"убийство\"}, {\"description\":\"грабёж\"}]")));

    open("/dashboard");
    $("[name=ssn]").val("1111111111").pressEnter();
    $("#result").shouldHave(text("Результат: обнаружен криминал. нельзя выпускать на волю."));
  }

  @Test
  public void noSafe_ifFailedToLoadHistory() {
    stubFor(get(urlEqualTo("/criminal-records?ssn=1111111111"))
        .willReturn(aResponse()
            .withStatus(504)));

    open("/dashboard");
    $("[name=ssn]").val("1111111111").pressEnter();
    $("#result").shouldHave(text("Результат: не удалось проверить историю преступлений. Нельзя выпускать на волю."));
  }
}
