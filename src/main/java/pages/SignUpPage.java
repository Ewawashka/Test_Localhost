package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import users.User;

import java.util.List;

public class SignUpPage extends AbstractPage {
    protected By singUpTitle = By.xpath("//h3[@class = 'title']");
    protected By emailField = By.xpath("//input[@type='email']");
    protected By nameField = By.xpath("//input[@type='text']");
    protected By passwordField = By.xpath("//input[@type='password']");
    protected By signUpButton = By.xpath("//button[contains(text(),'Sign Up')]");
    protected By notificationText = By.xpath("//div[@class='notification is-danger']");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return driver.findElement(singUpTitle).getText();
    }

    public SignUpPage setEmail(User user) {
        return setEmail(user.getEmail());
    }

    @Step("Заполняем поле почты = '{email}'")
    public SignUpPage setEmail(String email) {
        if (email != null) {
            driver.findElement(emailField).sendKeys(email);
        }
        return this;
    }

    public SignUpPage setPassword(User user) {
        return setPassword(user.getPassword());
    }

    @Step("Заполняем поле пароля = '{password}'")
    public SignUpPage setPassword(String password) {
        if (password != null) {
            driver.findElement(passwordField).sendKeys(password);
        }
        return this;
    }

    public SignUpPage setName(User user) {
        return setName(user.getName());
    }

    @Step("Заполняем поле имя значением = '{name}'")
    public SignUpPage setName(String name) {
        if (name != null) {
            driver.findElement(nameField).sendKeys(name);
        }
        return this;
    }

    public LoginPage clickOnSingUpButtonToCompleteRegistration() {
        driver.findElement(signUpButton).click();
        return new LoginPage(driver);
    }

    public String getNotification() {
        return driver.findElement(notificationText).getText();
    }

    public boolean isNotificationDisplayed() {
        List<WebElement> list = driver.findElements(notificationText);
        return !list.isEmpty() && list.get(0).isDisplayed();
    }

    @Override
    protected <T extends AbstractPage> T openPage() {
        return null;
    }
}
