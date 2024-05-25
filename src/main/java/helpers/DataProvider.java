package helpers;
import org.junit.jupiter.params.provider.Arguments;
import users.TestUsers;

import java.util.stream.Stream;
public class DataProvider {
    public static Stream<Arguments> successesRegistration(){

        return Stream.of(
                Arguments.of(TestUsers.validRegistrationUserAllFields),
                Arguments.of(TestUsers.validRegistrationUserRequiredFields)
        );
    }

    public static Stream<Arguments> registrationWithoutAllRequiredField(){
        return Stream.of(
                Arguments.of(TestUsers.invalidRegistrationUserEmptyEmail,"It is impossible to register a user with an empty email."),
                Arguments.of(TestUsers.invalidRegistrationUserNullEmail,"It is impossible to register a user with an empty email."),
                Arguments.of(TestUsers.invalidRegistrationUserEmptyPassword,"It is impossible to register a user with an empty password"),
                Arguments.of(TestUsers.getInvalidRegistrationUserNullPassword,"It is impossible to register a user with an empty password")
        );
    }

    public static Stream<Arguments> successesAuthorization(){
        return Stream.of(
                Arguments.of(TestUsers.validAuthorizationUserAllFields),
                Arguments.of((TestUsers.validAuthorizationUserRequiredFields))
        );
    }
    public static Stream<Arguments> authorizationWithoutAllRequiredField(){
        return Stream.of(
                Arguments.of(TestUsers.invalidAuthorizationUserEmptyEmail,"It is impossible to authorization a user with an empty email."),
                Arguments.of(TestUsers.invalidAuthorizationUserEmptyPassword,"It is impossible to authorization a user with an empty password")
                );
    }
    public static Stream<Arguments> userLoginWithWrongPassword(){
        return Stream.of(
                Arguments.of(TestUsers.validAuthorizationUserAllFields)
        );
    }


}

