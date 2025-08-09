package com.cab.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/com/cab/features",
    glue = {"com.cab.stepdefinitions", "com.cab.hooks"},
    plugin = {
        "pretty",
        "html:target/CucumberReport.html",
        "json:target/cucumber.json"
    }
)
public class TestRunner2 {
}