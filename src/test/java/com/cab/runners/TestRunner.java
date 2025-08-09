
package com.cab.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/com/cab/features",
    glue = {"com.cab.stepdefinitions", "com.cab.hooks"},
    plugin = {
        "pretty",
        "html:target/CucumberReport.html",
        "json:target/cucumber.json"
    }
)
public class TestRunner extends AbstractTestNGCucumberTests {
} 