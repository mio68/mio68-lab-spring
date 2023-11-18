package mio68.lab.spring.web.validation.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
public class Cat extends Animal {
    @NotBlank
    private String laziness;
}
