package validators;

import com.library.backend.validators.StrongPasswordValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrongPasswordValidatorTest {

    @Test
    void testValidPassword() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("StrongPwd1");

        // Assert
        assertTrue(isValid, "The password should be considered valid");
    }

    @Test
    void testInvalidPasswordNoDigit() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("NoDigitPwd");

        // Assert
        assertFalse(isValid, "The password should be considered invalid (missing digit)");
    }

    @Test
    void testInvalidPasswordNoLowercase() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("NOCASEPWD1");

        // Assert
        assertFalse(isValid, "The password should be considered invalid (missing lowercase)");
    }

    @Test
    void testInvalidPasswordNoUppercase() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("nocasepwd1");

        // Assert
        assertFalse(isValid, "The password should be considered invalid (missing uppercase)");
    }

    @Test
    void testInvalidPasswordWhitespace() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("Pwd With Spaces1");

        // Assert
        assertFalse(isValid, "The password should be considered invalid (contains whitespace)");
    }

    @Test
    void testInvalidPasswordShortLength() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid("Sh1");

        // Assert
        assertFalse(isValid, "The password should be considered invalid (short length)");
    }

    @Test
    void testInvalidPasswordNull() {
        // Arrange
        StrongPasswordValidator validator = new StrongPasswordValidator();

        // Act
        boolean isValid = validator.isValid(null);

        // Assert
        assertFalse(isValid, "Null password should be considered invalid");
    }

    @Test
    void testValidPasswordEdgeCaseMinLength() {
        // Test a password with the minimum length (6 characters)
        StrongPasswordValidator validator = new StrongPasswordValidator();
        boolean isValid = validator.isValid("Min6CA");

        assertTrue(isValid, "The password should be considered valid (minimum length)");
    }

    @Test
    void testValidPasswordEdgeCaseMaxLength() {
        // Test a password with the maximum length (assuming the maximum length is allowed)
        StrongPasswordValidator validator = new StrongPasswordValidator();
        assertTrue(validator.isValid("MaxPassword123456789"), "The password should be considered valid (maximum length)");
    }

    @Test
    void testInvalidPasswordEdgeCaseMaxLengthExceeded() {
        // Test a password that exceeds the assumed maximum length
        StrongPasswordValidator validator = new StrongPasswordValidator();
        assertFalse(validator.isValid("TooLongPassword12345678901"), "The password should be considered invalid (exceeds maximum length)");
    }
}

