package mio68.lab.spring.confprop.validation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("mio68.lab")
@Data
@Validated
public class OtherProperties {

    @NotBlank
    private String module;

}
