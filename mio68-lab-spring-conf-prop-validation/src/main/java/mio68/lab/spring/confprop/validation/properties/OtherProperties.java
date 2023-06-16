package mio68.lab.spring.confprop.validation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("mio68.lab")
@Data
@Validated
public class OtherProperties {

    @NotBlank
    private String module;

    @NotEmpty
    private List<String> cns = new ArrayList<>();

    private List<@NotBlank String> aliases = new ArrayList<>();

}
