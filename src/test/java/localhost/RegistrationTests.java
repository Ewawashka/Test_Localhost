package localhost;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LoginPage;
import pages.SignUpPage;
import users.User;

import static helpers.Properties.testProperties;
import static localhost.Helper.registerNewUser;
import static localhost.Helper.registerNewUserN;


public class RegistrationTests extends UiTest {

    @BeforeAll
    public static void dropUsers() {
        /*todo При подключении приложения к бд - реализовать метод.
        Удалить пользователей, используемых для проверки регистрации.
        Для удаления использовать api (если есть) или sql запрос в бд.
        */
    }

    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Успешная регистрация нового пользователя")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesRegistration")
    public void newUserSuccessesRegistration(User user) {
        LoginPage loginPage = registerNewUser(user, chromeDriver);
        Allure.step("Проверяем что пользователь зарегистрирован", () -> {
            boolean isRedirectComplete = loginPage.waitUrl(testProperties.localhostLoginPageUrl());
            Assertions.assertTrue(isRedirectComplete, "Произошел редирект на страницу авторизации");
        });
    }

    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Регистрация нового пользователя без заполнения обязательного поля")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#registrationWithoutAllRequiredField")
    public void registrationWithoutAllRequiredFieldTestN(User user, String errorMessage) {
        SignUpPage signUpPage = registerNewUserN(user, chromeDriver);
        Allure.step("Проверяем, что пользователь не зарегистрирован", () -> {
            Assertions.assertTrue(signUpPage.isNotificationDisplayed(), "Появилось сообщение об ошибке");
            Assertions.assertEquals(errorMessage, signUpPage.getNotification(), "Текст сообщения об ошибке соответствует ожидаемому");
            signUpPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostLoginPageUrl(), signUpPage.getCurrentUrl(), "Проверяем что не выполнился редирект на страницу авторизации");
        });

    }

    @Feature("Набор автотестов для проверки WEB формы регистрации и авторизации http://localhost:5000/ с помощью Page Object, все проверки в степах")
    @DisplayName("Повторная регистрация пользователя")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesAuthorization")
    public void secondRegistrationTestN(User user) {
        registerNewUser(user,chromeDriver); //todo После подключения к бд прогнать один раз и после удалить строку.
        SignUpPage signUpPage = registerNewUserN(user, chromeDriver);
        Allure.step("Проверяем, что пользователь не зарегистрирован", () -> {
            Assertions.assertTrue(signUpPage.isNotificationDisplayed(), "Появилось сообщение об ошибке");
            Assertions.assertEquals("Email address already exists. Go to login page.", signUpPage.getNotification(), "Текст сообщения об ошибке соответствует ожидаемому");
            signUpPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostLoginPageUrl(), signUpPage.getCurrentUrl(), "Проверяем что не выполнился редирект на страницу авторизации");
        });

    }



}
