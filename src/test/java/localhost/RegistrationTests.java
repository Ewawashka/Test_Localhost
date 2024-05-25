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
        /*todo ��� ����������� ���������� � �� - ����������� �����.
        ������� �������������, ������������ ��� �������� �����������.
        ��� �������� ������������ api (���� ����) ��� sql ������ � ��.
        */
    }

    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("�������� ����������� ������ ������������")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesRegistration")
    public void newUserSuccessesRegistration(User user) {
        LoginPage loginPage = registerNewUser(user, chromeDriver);
        Allure.step("��������� ��� ������������ ���������������", () -> {
            boolean isRedirectComplete = loginPage.waitUrl(testProperties.localhostLoginPageUrl());
            Assertions.assertTrue(isRedirectComplete, "��������� �������� �� �������� �����������");
        });
    }

    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("����������� ������ ������������ ��� ���������� ������������� ����")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#registrationWithoutAllRequiredField")
    public void registrationWithoutAllRequiredFieldTestN(User user, String errorMessage) {
        SignUpPage signUpPage = registerNewUserN(user, chromeDriver);
        Allure.step("���������, ��� ������������ �� ���������������", () -> {
            Assertions.assertTrue(signUpPage.isNotificationDisplayed(), "��������� ��������� �� ������");
            Assertions.assertEquals(errorMessage, signUpPage.getNotification(), "����� ��������� �� ������ ������������� ����������");
            signUpPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostLoginPageUrl(), signUpPage.getCurrentUrl(), "��������� ��� �� ���������� �������� �� �������� �����������");
        });

    }

    @Feature("����� ���������� ��� �������� WEB ����� ����������� � ����������� http://localhost:5000/ � ������� Page Object, ��� �������� � ������")
    @DisplayName("��������� ����������� ������������")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#successesAuthorization")
    public void secondRegistrationTestN(User user) {
        registerNewUser(user,chromeDriver); //todo ����� ����������� � �� �������� ���� ��� � ����� ������� ������.
        SignUpPage signUpPage = registerNewUserN(user, chromeDriver);
        Allure.step("���������, ��� ������������ �� ���������������", () -> {
            Assertions.assertTrue(signUpPage.isNotificationDisplayed(), "��������� ��������� �� ������");
            Assertions.assertEquals("Email address already exists. Go to login page.", signUpPage.getNotification(), "����� ��������� �� ������ ������������� ����������");
            signUpPage.waitForLoad();
            Assertions.assertNotEquals(testProperties.localhostLoginPageUrl(), signUpPage.getCurrentUrl(), "��������� ��� �� ���������� �������� �� �������� �����������");
        });

    }



}
