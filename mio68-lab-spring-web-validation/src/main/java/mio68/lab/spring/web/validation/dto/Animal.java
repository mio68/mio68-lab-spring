package mio68.lab.spring.web.validation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Animal {

    @NotBlank
    private String name;

}
