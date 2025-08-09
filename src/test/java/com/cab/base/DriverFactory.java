
package com.cab.base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.cab.config.ConfigReader;
import com.cab.utils.LoggerUtil;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;

public class DriverFactory {

    private static WebDriver driver;
    private static Properties prop;

   
    private static final Logger logger = LoggerUtil.getLogger(DriverFactory.class);

    public static WebDriver initDriver() {
        logger.info("Initializing WebDriver...");

        prop = ConfigReader.initProperties();
        String browser = prop.getProperty("browser").toLowerCase();
        logger.info("Browser specified in config: {}", browser);

        try {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    logger.info("Chrome browser launched successfully.");
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    logger.info("Firefox browser launched successfully.");
                    break;

                default:
                    logger.error("❌ Unsupported browser in config.properties: {}", browser);
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            long timeout = Long.parseLong(prop.getProperty("implicitWait"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            driver.manage().window().maximize();
            logger.info("Driver setup completed with implicit wait: {} seconds", timeout);
        } catch (Exception e) {
            logger.error("Error during driver initialization: {}", e.getMessage(), e);
            throw e;
        }

        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting the browser...");
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}
