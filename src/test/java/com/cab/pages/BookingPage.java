
package com.cab.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.cab.utils.Reports;
import com.cab.utils.LoggerUtil;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookingPage {

    WebDriver driver;
    private WebDriverWait wait;
    ExtentTest test;
    private static final Logger logger = LoggerUtil.getLogger(BookingPage.class);

    
    By fullName = By.id("fullname");
    By phoneNumber = By.id("phonenumber");
    By emailField = By.id("email");
    By tripLong = By.id("long");
    By tripLocal = By.id("local");
    By cabSelect = By.id("cabselect");
    By cabType = By.id("cabType");
    By pickupDate = By.id("pickupdate");
    By pickupTime = By.id("pickuptime");
    By passengerCount = By.id("passenger");
    By tripTypeOneway = By.id("oneway");
    By tripTypeRoundtrip = By.id("roundtrip");
    By bookNowButton = By.id("submitted");

    
    By emailError = By.id("confirm");
    By nameError = By.id("invalidname");
    By phoneError = By.id("invalidphno");
    By emailMissingError = By.id("invalidemail");
    By tripError = By.id("invalidtrip");
    By cabError = By.id("invalidcab");
    By passengerError = By.id("invalidcount");
    By confirmationMessage = By.id("confirm");

    public BookingPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

   
    private String normalizeOption(String input) {
        if (input == null) return "";
        return input.trim();
    }

    
    public void enterFullName(String name) {
        WebElement el = driver.findElement(fullName);
        el.clear();
        if (name != null && !name.trim().isEmpty()) {
            el.sendKeys(name);
            logger.info("Entered full name: {}", name);
        }
    }

    public void enterPhone(String phone) {
        WebElement el = driver.findElement(phoneNumber);
        el.clear();
        if (phone != null) {
            el.sendKeys(phone);
            logger.info("Entered phone number: {}", phone);
        }
    }

    public void enterEmail(String email) {
        WebElement el = driver.findElement(emailField);
        el.clear();
        if (email != null) {
            el.sendKeys(email);
            logger.info("Entered email: {}", email);
        }
    }

    public void selectTrip(String type) {
        if (type == null) return;
        if (type.equalsIgnoreCase("long")) {
            driver.findElement(tripLong).click();
            logger.info("Selected trip type: Long");
        } else if (type.equalsIgnoreCase("local")) {
            driver.findElement(tripLocal).click();
            logger.info("Selected trip type: Local");
        }
    }

    public void selectCab(String cab) {
        if (cab != null && !cab.trim().isEmpty()) {
            String visibleText = normalizeOption(cab).substring(0, 1) + normalizeOption(cab).substring(1).toLowerCase();
            new Select(driver.findElement(cabSelect)).selectByVisibleText(visibleText);
            logger.info("Selected cab: {}", visibleText);
        }
    }

    public void selectCabType(String cabTypeStr) {
        if (cabTypeStr != null && !cabTypeStr.trim().isEmpty()) {
            new Select(driver.findElement(cabType)).selectByVisibleText(normalizeOption(cabTypeStr));
            logger.info("Selected cab type: {}", cabTypeStr);
        }
    }

    public void enterPickupDate(String date) {
        WebElement el = driver.findElement(pickupDate);
        el.clear();
        if (date != null) {
            el.sendKeys(date);
            logger.info("Entered pickup date: {}", date);
        }
    }

    public void enterPickupTime(String time) {
        WebElement el = driver.findElement(pickupTime);
        el.clear();
        if (time != null) {
            el.sendKeys(time);
            logger.info("Entered pickup time: {}", time);
        }
    }

    public void selectPassengerCount(String count) {
        if (count != null && !count.trim().isEmpty()) {
            new Select(driver.findElement(passengerCount)).selectByVisibleText(count);
            logger.info("Selected passenger count: {}", count);
        }
    }

    public void chooseTripType(String tripType) {
        if (tripType == null) return;
        if (tripType.equalsIgnoreCase("oneway")) {
            driver.findElement(tripTypeOneway).click();
            logger.info("Selected trip mode: Oneway");
        } else if (tripType.equalsIgnoreCase("roundtrip")) {
            driver.findElement(tripTypeRoundtrip).click();
            logger.info("Selected trip mode: Roundtrip");
        }
    }

    public void clickBookNow() {
        logger.info("Clicking 'Book Now' button");
        driver.findElement(bookNowButton).click();
    }

    
    public String getConfirmationMessage() {
        String message = driver.findElement(confirmationMessage).getText();
        logger.info("Booking confirmation message: {}", message);
        return message;
    }

    public boolean validateInvalidName(String expected) {
        boolean result = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(nameError));
            WebElement errorElement = driver.findElement(nameError);
            String actual = errorElement.getText().trim();

            if (actual.equalsIgnoreCase(expected)) {
                result = true;
                logger.info("Name validation passed. Message shown: '{}'", actual);
            } else {
                logger.warn("Name validation failed. Expected: '{}', but got: '{}'", expected, actual);
            }

        } catch (TimeoutException te) {
            logger.error("Timeout waiting for name error: {}", te.getMessage());
            Reports.generateReport(driver, test, Status.FAIL,
                    "Name validation error not shown (Timeout)");
        } catch (Exception e) {
            logger.error("Unexpected exception during name validation: {}", e.getMessage(), e);
            Reports.generateReport(driver, test, Status.FAIL,
                    "Unexpected error during name validation: " + e.getMessage());
        }

        return result;
    }

    public String getPhoneError() {
        WebElement errorElement = driver.findElement(phoneError);
        String text = errorElement.getText().trim();
        if (text.isEmpty()) {
            text = errorElement.getAttribute("innerText");
            if (text == null || text.isEmpty()) {
                text = errorElement.getAttribute("textContent");
            }
        }
        logger.info("Phone error text retrieved: {}", text);
        return text;
    }

    public boolean getEmailError(String expected) {
        boolean actResult = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailError));
            WebElement errorElement = driver.findElement(emailError);
            String text = errorElement.getText().trim();

            if (text.equalsIgnoreCase("Your Booking has been Confirmed..")) {
                actResult = false;
                logger.warn("BUG: Booking confirmed with invalid email. Message shown: '{}'", text);
            } else {
                actResult = true;
                logger.info("Email validation message displayed: '{}'", text);
            }

        } catch (TimeoutException te) {
            logger.error("No message found in email error area (Timeout): {}", te.getMessage());
            Reports.generateReport(driver, test, Status.FAIL,
                    "No message found in email error area (Timeout)");
        }

        return actResult;
    }

    public String getEmailMissing() {
        WebElement errorElement = driver.findElement(emailMissingError);
        String text = errorElement.getText().trim();

        if (text.isEmpty()) {
            text = errorElement.getAttribute("innerText");
            if (text == null || text.isEmpty()) {
                text = errorElement.getAttribute("textContent");
            }
        }
        logger.info("Email missing error text retrieved: {}", text);
        return text;
    }

    public String getTripError() {
        String text = driver.findElement(tripError).getText();
        logger.info("Trip error text: {}", text);
        return text;
    }
    

    public String getCabError() {
        String text = driver.findElement(cabError).getText();
        logger.info("Cab error text: {}", text);
        return text;
    }

    public String getPassengerError() {
        String text = driver.findElement(passengerError).getText();
        logger.info("Passenger error text: {}", text);
        return text;
    }
}
