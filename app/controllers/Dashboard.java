package controllers;

import play.mvc.results.Result;
import play.rebel.RebelController;
import play.rebel.View;

public class Dashboard extends RebelController {

  public Result index() {
    return new View("dashboard/index.html").with("username", session.get("username"));
  }
}
