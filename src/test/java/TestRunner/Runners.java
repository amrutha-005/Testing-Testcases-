package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/features",
    glue = {"LoginStepDefinitions"},
    plugin = {
        "pretty",
        "html:reports/cucumber-reports.html"
    },
    monochrome = true
)
public class Runners extends AbstractTestNGCucumberTests {
}