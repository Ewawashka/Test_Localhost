package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import users.User;

import java.util.List;

import static helpers.Properties.testProperties;


public class LoginPage extends AbstractPage {
    protected By loginTitle = By.xpath("//h3[@class='title']");
    protected By emailField = By.xpath("//form//input[@type='email']");
    protected By passwordField = By.xpath("//form//input[@type='password']");
    protected By rememberMeCheckbox = By.xpath("//input[@type='checkbox']");
    protected By loginFieldButton = By.xpath("//button[contains(text(),'Login')]");
    protected By notificationText = By.xpath("//div[@class='notification is-danger']");

    @Override
    protected <T extends AbstractPage> T openPage() {
        return null;
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return driver.findElement(loginTitle).getText();
    }

    public LoginPage setEmail(User user) {
        return setEmail(user.getEmail());
    }

    @Step("Заполняем поле почты = '{email}'")
    public LoginPage setEmail(String email){
        if(email!=null){
            driver.findElement(emailField).sendKeys(email);
        }
        return this;
    }

    public LoginPage setPassword(User user) {
        driver.findElement(passwordField).sendKeys(user.getPassword());
        return this;
    }

    @Step("Заполняем поле пароля = '{password}'")
    public LoginPage setPassword(String password) {
        if(password!=null){
        driver.findElement(passwordField).sendKeys(password);}
        return this;
    }

    @Step("Нажимаем на кнопку 'Login'")
    public ProfilePage clickLoginButtonToCompleteLogin() {
        driver.findElement(loginFieldButton).click();
        return new ProfilePage(driver);
    }

    public String getNotification(){
        return driver.findElement(notificationText).getText();
    }

    public boolean isNotificationDisplayed(){
        List<WebElement> list = driver.findElements(notificationText);
        return !list.isEmpty()&&list.get(0).isDisplayed();
    }
}
