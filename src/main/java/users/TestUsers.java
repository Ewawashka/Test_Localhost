package users;

public class TestUsers {
    /*
    Данные пользователи используются в тестах на регистрацию.
    При подключении к бд, данные об этих пользователях будут удаляться перед началом тестов.
    Не переиспользуйте их или используйте с осторожностью.
     */
    public static User validRegistrationUserRequiredFields = new User("validRegistrationUserRequiredFields@mail.ru");
    public static User validRegistrationUserAllFields = new User("validRegistrationUserAllFields@mail.ru").setName("validRegistrationUserAllFields");
    public static User invalidRegistrationUserEmptyEmail = new User("").setName("invalidRegistrationUserEmptyEmail");
    public static User invalidRegistrationUserEmptyPassword = new User("invalidRegistrationUserEmptyPassword@mail.ru","invalidRegistrationUserEmptyPassword","");
    public static User invalidRegistrationUserNullEmail = new User().setName("invalidRegistrationUserNullEmail").setPassword("1234");
    public static User getInvalidRegistrationUserNullPassword = new User("getInvalidRegistrationUserNullPassword@mail.ru","getInvalidRegistrationUserNullPassword",null);


    public static User validAuthorizationUserRequiredFields = new User("validAuthorizationUserRequiredFields@mail.ru");
    public static User validAuthorizationUserAllFields = new User("validAuthorizationUserAllFields@mail.ru").setName("validAuthorizationUserAllFields");
    public static User invalidAuthorizationUserEmptyEmail = new User("").setName("invalidAuthorizationUserEmptyEmail");
    public static User invalidAuthorizationUserEmptyPassword = new User("invalidAuthorizationUserEmptyPassword@mail.ru","invalidAuthorizationUserEmptyPassword","");
}
