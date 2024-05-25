package localhost;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import users.User;

import static helpers.Properties.testProperties;
import static localhost.Helper.login;
import static localhost.Helper.loginN;

public class AuthorizationTest extends UiTest {

    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Успешная авторизация")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesAuthorization")
    public void userLoginTest(User user) {
        Helper.registerNewUser(user, chromeDriver); //todo После подключения к бд прогнать один раз и после удалить строку.
        ProfilePage profilePage = login(user, chromeDriver);
        Allure.step("Проверяем что пользователь авторизирован", () -> {
            boolean isRedirectComplete = profilePage.waitUrl(testProperties.localhostProfilePageUrl());
            Assertions.assertTrue(isRedirectComplete, "Произошел редирект на  страницу профиля");
            String expectedTitle = user.getName() == null || user.getName().isEmpty() ? "Welcome!" : "Welcome, " + user.getName() + "!";
            Assertions.assertEquals(expectedTitle, profilePage.getTitleText(),"Приветствие пользователя соответствует ожидаемому");
        });
        Allure.step("Выходим из профиля", () -> {
            HomePage homePage = profilePage.clickOnLogoutButton();
            boolean isRedirectComplete = homePage.waitUrl(testProperties.localhostUrl());
            Assertions.assertTrue(isRedirectComplete, "Произошел редирект на домашнюю страницу ");
        });
    }

    //Тест может падать из-за ошибки, позволяющей регистрировать пользователя с пустым полем email.
    //Если RegistrationTests был запущен раньше - то создастся пользователь с пустым email и данный тест пройдет авторизацию.
    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Попытка авторизации")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#authorizationWithoutAllRequiredField")
    public void userLoginTestN(User user) {
        User userForRegistration = new User()
                .setEmail(user.getEmail() == null || user.getEmail().isEmpty() ? "testEmailForRegistration@mail.ru" : user.getEmail())
                .setPassword(user.getPassword() == null || user.getPassword().isEmpty() ? "1234" : user.getPassword())
                .setName(user.getName());
        Helper.registerNewUser(userForRegistration, chromeDriver); //todo После подключения к бд прогнать один раз и после удалить строки от начала теста и до текущей (включительно).
        LoginPage loginPage = loginN(user, chromeDriver);
        Allure.step("Проверяем, что пользователь не авторизировался", () -> {
            Assertions.assertTrue(loginPage.isNotificationDisplayed(), "Появилось сообщение об ошибке");
            Assertions.assertEquals("Please check your login details and try again.", loginPage.getNotification(), "Текст сообщения об ошибке соответствует ожидаемому");
            loginPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostProfilePageUrl(), loginPage.getCurrentUrl(), "Проверяем что не выполнился редирект на страницу авторизации");
        });

    }

    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Попытка авторизации")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#userLoginWithWrongPassword")
    public void userLoginWithWrongPasswordTestN(User user) {
        Helper.registerNewUser(user, chromeDriver); //todo После подключения к бд прогнать один раз и после удалить строку.
        User userWithWrongPassword = new User()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setPassword(user.getPassword() + "wrong");
        LoginPage loginPage = loginN(userWithWrongPassword, chromeDriver);
        Allure.step("Проверяем, что пользователь не авторизировался", () -> {
            Assertions.assertTrue(loginPage.isNotificationDisplayed(), "Появилось сообщение об ошибке");
            Assertions.assertEquals("Please check your login details and try again.", loginPage.getNotification(), "Текст сообщения об ошибке соответствует ожидаемому");
            loginPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostProfilePageUrl(), loginPage.getCurrentUrl(), "Проверяем что не выполнился редирект на страницу авторизации");
        });

    }

}
