package example;

import org.junit.Test;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConversationTest {

    @Test
    public void shouldBeValidWithValidEmailAddress() {
        Conversation conversation = new Conversation();
        conversation.setValue("spam@mailinator.com");

        assertTrue("Conversation should be valid", conversation.validate());
    }

    @Test
    public void shouldHaveErrorsForAnInvalidEmailAddress() {
        Conversation conversation = new Conversation();
        conversation.setValue("test");

        assertFalse("Conversation should not be valid", conversation.validate());

        BindingResult result = conversation.createBindingResult("conversation");

        assertTrue("Should have field error for value", result.hasFieldErrors("value"));
    }

    @Test
    public void shouldHaveErrorsForANullEmailAddress() {
        Conversation conversation = new Conversation();
        conversation.setValue(null);

        assertFalse("Conversation should not be valid", conversation.validate());

        BindingResult result = conversation.createBindingResult("conversation");

        assertTrue("Should have field error for value", result.hasFieldErrors("value"));
    }
}
