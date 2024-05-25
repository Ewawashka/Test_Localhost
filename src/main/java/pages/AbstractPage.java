package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public abstract class AbstractPage {
    protected Wait<WebDriver> waitElement;
    protected WebDriver driver;
    protected By homeButton = By.xpath("//div[@class='navbar-end']//a[contains(text(),'Home')]");
    protected By loginButton = By.xpath("//div[@class='navbar-end']//a[contains(text(),'Login')]");
    protected By signUpButton = By.xpath("//div[@class='navbar-end']//a[contains(text(),'Sign Up')]");

    protected abstract <T extends AbstractPage> T openPage();

    public void waitForLoad() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        waitElement = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Ожидаем пока url будет = {expectedUrl}")
    public boolean waitUrl(String expectedUrl) {
        try {
            return waitElement.until(ExpectedConditions.urlToBe(expectedUrl)).booleanValue();
        } catch (TimeoutException e) {
            return false;
        }
    }
}


