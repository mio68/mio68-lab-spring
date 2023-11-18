package mio68.lab.spring.web.validation.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
@Valid
public class Dog extends Animal {
    @NotBlank
    private String barking;
}
