package mio68.lab.spring.web.validation.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Pets {
    @NotNull
    private AnimalType type;

    @NotBlank
    private String description;

    @Valid
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dogs.class, name = "DOG"),
            @JsonSubTypes.Type(value = Cats.class, name = "CAT")
    })
    private Animals animals;
}
