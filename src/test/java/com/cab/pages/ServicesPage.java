
package com.cab.pages;

import org.openqa.selenium.*;
import com.cab.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class ServicesPage {

    WebDriver driver;
    private static final Logger logger = LoggerUtil.getLogger(ServicesPage.class);

    // POM locators
    private final By miniCab = By.cssSelector("a[href='mini.html']");
    private final By microCab = By.cssSelector("a[href='micro.html']");
    private final By sedanCab = By.cssSelector("a[href='sedan.html']");
    private final By suvCab = By.cssSelector("a[href='suv.html']");

    public ServicesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCabLink(String cabType) {
        try {
            switch (cabType.toLowerCase().trim()) {
                case "mini":
                    logger.info("Clicking Mini cab link");
                    driver.findElement(miniCab).click();
                    break;
                case "micro":
                    logger.info("Clicking Micro cab link");
                    driver.findElement(microCab).click();
                    break;
                case "sedan":
                    logger.info("Clicking Sedan cab link");
                    driver.findElement(sedanCab).click();
                    break;
                case "suv":
                    logger.info("Clicking SUV cab link");
                    driver.findElement(suvCab).click();
                    break;
                default:
                    logger.error("Invalid cab type passed: '{}'", cabType);
                    throw new IllegalArgumentException("Invalid cab type: " + cabType);
            }
        } catch (NoSuchElementException e) {
            logger.error("Cab link not found for type '{}': {}", cabType, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error clicking cab link '{}': {}", cabType, e.getMessage(), e);
            throw e;
        }
    }

    public String getCurrentURL() {
        String url = driver.getCurrentUrl();
        logger.info("Current page URL: {}", url);
        return url;
    }
}
