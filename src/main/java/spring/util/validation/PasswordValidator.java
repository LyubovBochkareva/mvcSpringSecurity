package spring.util.validation;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {

    public static boolean validatePassword(String password, String confirmPassword) {
        if (confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }
}
