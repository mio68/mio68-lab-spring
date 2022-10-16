package mio68.lab.spring.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PersonDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
