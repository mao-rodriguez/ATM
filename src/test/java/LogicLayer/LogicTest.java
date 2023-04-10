package LogicLayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LogicTest {

    @Test
    @DisplayName("Check for valid username strings.")
    void TestisValidUsername() {
        Logic logic = new Logic();
        assertTrue(logic.isValidUserName("0123ABCXYZabcxyz"),"Given string must contain only valid characters to return true.");
        assertFalse(logic.isValidUserName("%&"), "Given invalid characters must return false.");
        assertFalse(logic.isValidUserName(""), "Given string can't be empty.");
    }
}