package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    glue = "org.example",
    features = "classpath:.",
    plugin = "html:target/cucumber-html-report/demo-report.html")
public class RunCucumberTest {
  public RunCucumberTest() {}
}
