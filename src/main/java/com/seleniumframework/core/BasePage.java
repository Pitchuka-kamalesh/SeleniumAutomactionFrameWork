package com.seleniumframework.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.seleniumframework.core.DriverManager.getDriver;
/**
 * Base class for page objects containing common Selenium functions.
 */
public class BasePage {
    // i must implements all the selenium common functions here.
    private static final Logger log = LogManager.getLogger(BasePage.class.getName());
    /**
     * Initializes the page elements using PageFactory.
     */
    public BasePage() {
        log.info("BasePage.BasePage()");
        PageFactory.initElements(getDriver(), this);
    }
    /**
     * Waits for the visibility of a WebElement.
     *
     * @param element The WebElement to wait for.
     */
    public static void waitForElement(WebElement element) {
        log.info("BasePage.waitForElement() -> {} ", "wait for 5 seconds");
        WebDriverWait wait = new WebDriverWait(getDriver(), Timeouts.SHORT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    /**
     * Clears the text box and sends keys to it.
     *
     * @param element The WebElement representing the text box.
     * @param value   The value to send to the text box.
     */
    public static void sendKeys(WebElement element, String value) {
        waitForElement(element);
        log.info("BasePage.sendKeys() -> {} ", "Clearing the textBox");
        element.clear();
        log.info("BasePage.sendKeys() -> {} -> {} ", "sending the value to textBox", value);
        element.sendKeys(value);
    }
    /**
     * Safely clicks on a WebElement after waiting for it to be clickable.
     *
     * @param element The WebElement to click on.
     */
    public static void safeClick(WebElement element) {
        waitForElement(element);
        log.info("BasePage.safeClick()");
        element.click();
    }

    /**
     * Retrieves the text from a WebElement safely by waiting for the element to be present.
     *
     * @param element The WebElement from which to retrieve text.
     * @return The text content of the WebElement.
     */
    public static String getSafeText(WebElement element){
        waitForElement(element);
        log.info("BasePage.getSafeText()");
        return element.getText();

    }

    /**
     * Checks if a WebElement is present and displayed.
     *
     * @param element The WebElement to check for presence and visibility.
     * @return True if the WebElement is present and displayed, false otherwise.
     */
    public static boolean checkElementIsPresent(WebElement element){

        return element.isDisplayed();
    }

    public static void acceptAlert(){
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public static String getUrl(){

        return getDriver().getCurrentUrl();

    }

}
