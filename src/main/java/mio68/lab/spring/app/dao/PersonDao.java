package mio68.lab.spring.app.dao;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PersonDao {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
