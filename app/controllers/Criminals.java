package controllers;

import models.Verdict;
import play.mvc.results.Result;
import play.rebel.RebelController;
import play.rebel.View;
import services.CriminalSafetyCalculator;

import javax.inject.Inject;

public class Criminals extends RebelController {
  @Inject
  private CriminalSafetyCalculator criminalSafetyCalculator;

  public Result check(String ssn) {
    Verdict verdict = criminalSafetyCalculator.check(ssn);

    return new View("criminals/check.html")
        .with("ssnSafe", ssn)
        .with("verdict", verdict);
  }
}
