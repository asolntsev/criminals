package controllers;

import play.mvc.Controller;
import play.mvc.results.Result;
import play.rebel.View;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;

public class Dashboard extends Controller {

  public Result index(String q) {
    return new View("dashboard/index.html").with("name", requireNonNullElseGet(q, () -> requireNonNullElse(session.get("user.name"), "")));
  }
}
