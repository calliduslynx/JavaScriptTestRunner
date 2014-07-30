package de.mama.javascripttestrunner;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

public class JasmineReader implements JavaScriptTestRunReader {

    /**
     * The driver to interact with the firefox
     */
    private WebDriver driver;
    /**
     * Selenium, offers possibilities to get information about WebElements displayed by the browser.
     */
    private Selenium web;

    @Override
    public void startUp() {
        driver = new FirefoxDriver();
        web = new WebDriverBackedSelenium(driver, "http://www.google.de");
    }

    @Override
    public void cleanUp() {
        web.close();
    }

    /*
     * Opens the given url in the firefox and parse the given html file.
     */
    @Override
    public List<JavaScriptTestRun> readFrom(String jsTestUrl) {
        List<JavaScriptTestRun> testRuns = new ArrayList<JavaScriptTestRun>();
        web.open(jsTestUrl); // load the url

        if (web.isElementPresent("css=.summaryMenuItem") && web.isVisible("css=.summaryMenuItem")) {
            web.click("css=.summaryMenuItem"); // click one of the menu-button to get to the resultpage
        }

        // Get all single specs as WebElement
        List<WebElement> specs = driver.findElements(By.cssSelector(" .specSummary"));

        for (WebElement spec : specs) { // for each single test / spec ...
            // set status
            boolean success = false;
            if (spec.getAttribute("class").contains("passed")) {
                success = true;
            }

            // and get parents and set name until first group is reached.
            String name = spec.getText();
            while (!specIsTopLevel(spec)) {
                name = spec.findElement(By.xpath("parent::*")).findElement(By.cssSelector("a")).getText() + " -> " + name;
                spec = spec.findElement(By.xpath("parent::*"));
            }
            testRuns.add(new JavaScriptTestRun(name, success));
        }

        return testRuns;
    }

    /**
     * Compares the given WebElement with each WebElement that is represented by the 'top-level-suites'.
     *
     * @param spec
     *            the WebElement that must be checked, if it represents a Top-Level-Suite
     * @return true if the spec matches one of the top-level-suites.
     */
    private boolean specIsTopLevel(WebElement spec) {
        List<WebElement> topLevelSuites = driver.findElements(By.cssSelector(".summary > .suite"));
        for (WebElement topLevelSuite : topLevelSuites) {
            if (spec.equals(topLevelSuite)) {
                return true;
            }
        }
        return false;
    }

}