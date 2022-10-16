package mio68.lab.spring.webapp.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PersonDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
