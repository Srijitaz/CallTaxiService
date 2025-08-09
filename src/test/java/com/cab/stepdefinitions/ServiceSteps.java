package com.cab.stepdefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.cab.base.DriverFactory;
import com.cab.pages.ServicesPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ServiceSteps {
	
	WebDriver driver;
	ServicesPage servicesPage;

	@Given("User opens the Services page")
	public void open_services_page() {
	    driver = DriverFactory.getDriver();
	    driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/services.html");
	    servicesPage = new ServicesPage(driver);
	}

	@When("User clicks on {string} service link")
	public void user_clicks_service_link(String cabType) {
	    driver = DriverFactory.getDriver();
	    servicesPage = new ServicesPage(driver);

	    String originalHandle = driver.getWindowHandle();
	    servicesPage.clickCabLink(cabType);

	   
	    for (String handle : driver.getWindowHandles()) {
	        if (!handle.equals(originalHandle)) {
	            driver.switchTo().window(handle);
	            break;
	        }
	    }
	}

	@Then("The user should be navigated to {string}")
	public void verify_navigation(String expectedPage) {
	    String currentURL = servicesPage.getCurrentURL();
	    Assert.assertTrue(currentURL.contains(expectedPage));
	}
}
