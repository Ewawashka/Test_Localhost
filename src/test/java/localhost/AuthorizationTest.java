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

    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("�������� �����������")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesAuthorization")
    public void userLoginTest(User user) {
        Helper.registerNewUser(user, chromeDriver); //todo ����� ����������� � �� �������� ���� ��� � ����� ������� ������.
        ProfilePage profilePage = login(user, chromeDriver);
        Allure.step("��������� ��� ������������ �������������", () -> {
            boolean isRedirectComplete = profilePage.waitUrl(testProperties.localhostProfilePageUrl());
            Assertions.assertTrue(isRedirectComplete, "��������� �������� ��  �������� �������");
            String expectedTitle = user.getName() == null || user.getName().isEmpty() ? "Welcome!" : "Welcome, " + user.getName() + "!";
            Assertions.assertEquals(expectedTitle, profilePage.getTitleText(),"����������� ������������ ������������� ����������");
        });
        Allure.step("������� �� �������", () -> {
            HomePage homePage = profilePage.clickOnLogoutButton();
            boolean isRedirectComplete = homePage.waitUrl(testProperties.localhostUrl());
            Assertions.assertTrue(isRedirectComplete, "��������� �������� �� �������� �������� ");
        });
    }

    //���� ����� ������ ��-�� ������, ����������� �������������� ������������ � ������ ����� email.
    //���� RegistrationTests ��� ������� ������ - �� ��������� ������������ � ������ email � ������ ���� ������� �����������.
    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("������� �����������")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#authorizationWithoutAllRequiredField")
    public void userLoginTestN(User user) {
        User userForRegistration = new User()
                .setEmail(user.getEmail() == null || user.getEmail().isEmpty() ? "testEmailForRegistration@mail.ru" : user.getEmail())
                .setPassword(user.getPassword() == null || user.getPassword().isEmpty() ? "1234" : user.getPassword())
                .setName(user.getName());
        Helper.registerNewUser(userForRegistration, chromeDriver); //todo ����� ����������� � �� �������� ���� ��� � ����� ������� ������ �� ������ ����� � �� ������� (������������).
        LoginPage loginPage = loginN(user, chromeDriver);
        Allure.step("���������, ��� ������������ �� ���������������", () -> {
            Assertions.assertTrue(loginPage.isNotificationDisplayed(), "��������� ��������� �� ������");
            Assertions.assertEquals("Please check your login details and try again.", loginPage.getNotification(), "����� ��������� �� ������ ������������� ����������");
            loginPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostProfilePageUrl(), loginPage.getCurrentUrl(), "��������� ��� �� ���������� �������� �� �������� �����������");
        });

    }

    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("������� �����������")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#userLoginWithWrongPassword")
    public void userLoginWithWrongPasswordTestN(User user) {
        Helper.registerNewUser(user, chromeDriver); //todo ����� ����������� � �� �������� ���� ��� � ����� ������� ������.
        User userWithWrongPassword = new User()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setPassword(user.getPassword() + "wrong");
        LoginPage loginPage = loginN(userWithWrongPassword, chromeDriver);
        Allure.step("���������, ��� ������������ �� ���������������", () -> {
            Assertions.assertTrue(loginPage.isNotificationDisplayed(), "��������� ��������� �� ������");
            Assertions.assertEquals("Please check your login details and try again.", loginPage.getNotification(), "����� ��������� �� ������ ������������� ����������");
            loginPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostProfilePageUrl(), loginPage.getCurrentUrl(), "��������� ��� �� ���������� �������� �� �������� �����������");
        });

    }

}
