
package com.cab.stepdefinitions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.cab.base.DriverFactory;
import com.cab.pages.BookingPage;
import com.cab.utils.ExcelUtil;
import com.cab.utils.LoggerUtil;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;

public class BookingStepsExcel {

    WebDriver driver;
    BookingPage bookingPage;
    List<Map<String, String>> data;
    Map<String, String> testRow;

    private static final Logger logger = LoggerUtil.getLogger(BookingStepsExcel.class);

    @Given("User launches the browser and opens the cab booking page")
    public void user_opens_booking_page() {
        driver = DriverFactory.getDriver();
        driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/booking.html");
        bookingPage = new BookingPage(driver);
        logger.info("Browser launched and booking page opened.");
    }

    @When("User fills the form with valid data:")
    public void user_fills_form(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> formData = dataTable.asMaps().get(0);
        logger.info("Filling form with DataTable: {}", formData);
        bookingPage.enterFullName(formData.get("Name"));
        bookingPage.enterPhone(formData.get("Phone"));
        bookingPage.enterEmail(formData.get("Email"));
        bookingPage.selectTrip(formData.get("Trip"));
        bookingPage.selectCab(formData.get("Cab"));
        bookingPage.selectCabType(formData.get("CabType"));
        bookingPage.enterPickupDate(formData.get("Date"));
        bookingPage.enterPickupTime(formData.get("Time"));
        bookingPage.selectPassengerCount(formData.get("Passenger"));
        bookingPage.chooseTripType(formData.get("TripType"));
    }

    @When("User reads booking data from Excel sheet {string} and row {int}")
    public void user_reads_excel_data(String sheet, Integer rowNum) {
        logger.info("Reading booking data from Excel sheet '{}' at row {}", sheet, rowNum);
        driver = DriverFactory.getDriver();
        driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/booking.html");
        bookingPage = new BookingPage(driver);

        data = ExcelUtil.getData(sheet);

        if (data == null || data.isEmpty()) {
            logger.error("No data found in Excel sheet: {}", sheet);
            throw new RuntimeException("❌ No data found in Excel sheet: " + sheet);
        }

        if (rowNum < 0 || rowNum >= data.size()) {
            logger.error("Row {} is out of bounds. Sheet '{}' has {} rows.", rowNum, sheet, data.size());
            throw new IndexOutOfBoundsException("❌ Row " + rowNum + " is out of bounds.");
        }

        testRow = data.get(rowNum);
        logger.info("Loaded Excel row data: {}", testRow);
    }

    @When("User fills the form using Excel data")
    public void user_fills_form_excel() {
        if (testRow == null) {
            logger.error("Test row data is null. Make sure 'user_reads_excel_data' ran.");
            throw new RuntimeException("Test row data is null.");
        }

        logger.info("Filling form using Excel data.");
        bookingPage.enterFullName(testRow.get("Name"));
        bookingPage.enterPhone(testRow.get("Phone"));
        bookingPage.enterEmail(testRow.get("Email"));
        bookingPage.selectTrip(testRow.get("Trip"));
        bookingPage.selectCab(testRow.get("Cab"));
        bookingPage.selectCabType(testRow.get("CabType"));
        bookingPage.enterPickupDate(testRow.get("Date"));
        bookingPage.enterPickupTime(testRow.get("Time"));
        bookingPage.selectPassengerCount(testRow.get("Passenger"));
        bookingPage.chooseTripType(testRow.get("TripType"));
    }

    @And("User clicks Book Now button")
    public void user_clicks_book_now() {
        bookingPage.clickBookNow();
        logger.info("Clicked on Book Now button.");
    }

    @Then("Booking confirmation message {string} should be displayed")
    public void booking_confirmation_should_be_displayed(String expected) {
        String actual = bookingPage.getConfirmationMessage();
        logger.info("Validating confirmation message. Expected: '{}', Actual: '{}'", expected, actual);
        Assert.assertTrue(actual.contains(expected));
    }

    @Then("Email error message {string} should be displayed")
    public void email_error_should_be_displayed(String expected) {
        String actual = bookingPage.getEmailMissing();
        logger.info("Validating missing email error. Expected: '{}', Actual: '{}'", expected, actual);
        Assert.assertTrue(actual.contains(expected));
    }

    @Then("Error message {string} should be shown under Name field")
    public void validate_name_error(String expected) {
        logger.info("Validating Name field error: '{}'", expected);
        Assert.assertTrue(bookingPage.validateInvalidName(expected));
    }

    @Then("Error message {string} should be shown under Trip selection")
    public void trip_error_displayed(String expected) {
        logger.info("Validating Trip selection error: '{}'", expected);
        Assert.assertTrue(bookingPage.getTripError().contains(expected));
    }
    @Then("Error message {string} should be shown under Cab Type selection")
    public void cab_error_displayed(String expected) {
        logger.info("Validating Cab selection error: '{}'", expected);
        Assert.assertTrue(bookingPage.getCabError().contains(expected));
    }

    @Then("Error message {string} should be shown under Passenger count")
    public void passenger_error_displayed(String expected) {
        logger.info("Validating Passenger count error: '{}'", expected);
        Assert.assertTrue(bookingPage.getPassengerError().contains(expected));
    }

    @Then("Error message {string} should be shown under Email field")
    public void email_error_displayed(String expected) {
        logger.info("Validating Email format error: '{}'", expected);
        Boolean actual = bookingPage.getEmailError(expected);
        Assert.assertTrue(actual);
    }

    @Then("Error message {string} should be shown under Phone field")
    public void phone_error_displayed(String expected) {
        String actual = bookingPage.getPhoneError();
        logger.info("Validating Phone field error. Expected: '{}', Actual: '{}'", expected, actual);
        Assert.assertTrue(actual.contains(expected));
    }
}
