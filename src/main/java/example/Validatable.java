package example;

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

public class Validatable {

    private final MultiValueMap<String, String> errors = new LinkedMultiValueMap<String, String>();

    public boolean validate() {
        errors.clear();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Validatable>> violations = validator.validate(this);
        for (ConstraintViolation<Validatable> violation : violations) {
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
}
