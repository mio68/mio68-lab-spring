package mio68.lab.spring.rest.errorhandling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PersonDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
