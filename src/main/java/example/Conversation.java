package example;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Conversation {

    public static final String CONVERSATION_ID = "conversationId";

    private final String id = UUID.randomUUID().toString();

    private final MultiValueMap<String, String> errors = new LinkedMultiValueMap<String, String>();

    @NotBlank(message = "Value cannot be blank")
    @Email(message = "Value should be an email address")
    private String value;

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean validate() {
        errors.clear();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Conversation>> violations = validator.validate(this);
        for (ConstraintViolation<Conversation> violation : violations) {
            errors.add(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return errors.isEmpty();
    }

    public Map<String, Object> createModel(String beanName) {
        return createBindingResult(beanName).getModel();
    }

    public BindingResult createBindingResult(String beanName) {
        BindingResult result = new BeanPropertyBindingResult(this, beanName);
        for (String property : errors.keySet()) {
            for (String error : errors.get(property)) {
                result.rejectValue(property, error, error);
            }
        }
        return result;
    }

    public DomainObject createDomainObject() {
        return new DomainObject(value);
    }

    @Override
    public String toString() {
        return String.format("Conversation[%s]", getId());
    }
}
