package localhost;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import pages.SignUpPage;
import users.User;

public class Helper {
    @Step("������������ ������ ������������")
    public static LoginPage registerNewUser(User user, WebDriver driver) {
        return setUserRegistrationFields(user, driver).clickOnSingUpButtonToCompleteRegistration();
    }

    @Step("������������ ������ ������������ � ����������� �����������")
    public static SignUpPage registerNewUserN(User user, WebDriver driver) {
        SignUpPage signUpPage = setUserRegistrationFields(user, driver);
        signUpPage.clickOnSingUpButtonToCompleteRegistration();
        return signUpPage;
    }

    public static SignUpPage setUserRegistrationFields(User user, WebDriver driver) {
        return new HomePage(driver)
                .openPage()
                .clickSingUpButton()
                .setPassword(user)
                .setEmail(user)
                .setName(user);
    }

    @Step("������������")
    public static ProfilePage login(User user, WebDriver driver){
        return setUserAuthorizationFields(user,driver).clickLoginButtonToCompleteLogin();
    }

    @Step("������� �����������")
    public static LoginPage loginN(User user,WebDriver driver){
        LoginPage loginPage = setUserAuthorizationFields(user,driver);
        loginPage.clickLoginButtonToCompleteLogin();
        return loginPage;

    }

    public static LoginPage setUserAuthorizationFields(User user, WebDriver driver){
        return new HomePage(driver)
                .openPage()
                .clickLoginButton()
                .setEmail(user)
                .setPassword(user);

    }
}
