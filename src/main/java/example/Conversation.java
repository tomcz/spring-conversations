package example;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

public class Conversation extends Validatable {

    public static final String CONVERSATION_ID = "id";

    private final String id = UUID.randomUUID().toString();

    @NotBlank(message = "Value cannot be blank")
    @Email(message = "Value should be an email address")
    private String value;

    private String cancel;

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public boolean isCancelled() {
        return cancel != null;
    }

    public DomainObject createDomainObject() {
        return new DomainObject(value);
    }

    @Override
    public String toString() {
        return String.format("Conversation[%s]", getId());
    }
}
