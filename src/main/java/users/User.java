package users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String name,String password){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /**
     * По умолчанию подставляется пароль User.password = 1234
     * @param email - email пользователя
     */
    public User(String email) {
        this.email = email;
        this.password = "1234";
    }
}
