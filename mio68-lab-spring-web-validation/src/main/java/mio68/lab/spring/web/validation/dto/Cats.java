package mio68.lab.spring.web.validation.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Cats implements Animals{
    @NotEmpty
    private List<@Valid Cat> list;

}
