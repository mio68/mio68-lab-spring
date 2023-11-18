package mio68.lab.spring.web.validation.dto;

import lombok.Data;
import lombok.val;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Validated
@Valid
public class Dogs extends ArrayList<Dog> implements Animals{
//    @NotEmpty
//    private List<@Valid Dog> list;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @Override
    public boolean add(Dog dog) {
        val constraintViolations = validator.validate(dog);
        System.out.println(constraintViolations);
        return super.add(dog);
    }

    public void validateDog(@Validated Dog dog) {

    }
}
