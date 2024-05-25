package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static helpers.Properties.testProperties;


public class HomePage extends AbstractPage {

    protected By homePageTitle = By.xpath("//h1[@class = 'title']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Получаем текст из заголовка")
    public String getTitleText() {

        return driver.findElement(homePageTitle).getText();
    }

    @Step("Нажимаем на кнопку Login")
    public LoginPage clickLoginButton() {
        driver.findElement(loginButton).click();
        waitElement.until(ExpectedConditions.urlContains(testProperties.localhostLoginPageUrl()));
        return new LoginPage(driver);
    }

    @Step("Нажимаем на кнопку Sign Up")
    public SignUpPage clickSingUpButton() {
        driver.findElement(signUpButton).click();
        waitElement.until(ExpectedConditions.urlContains(testProperties.localhostSingUpPageUrl()));
        return new SignUpPage(driver);
    }

    @Step("Кнопка Home отображается")
    public Boolean homeButtonIsDisplayed() {
        return driver.findElement(homeButton).isDisplayed();
    }

    @Step("Кнопка Login отображается")
    public Boolean loginButtonIsDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    @Step("Кнопка Sign Up  отображается")
    public Boolean signUpButtonIsDisplayed() {
        return driver.findElement(signUpButton).isDisplayed();
    }

    @Step("Открываем страницу в браузере")
    @Override
    public HomePage openPage() {
        String url = testProperties.localhostUrl();
        Allure.step("Переходим по url = " + url, () -> driver.get(url));
        return this;
    }
}
