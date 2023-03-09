package mio68.lab.spring.confprop.validation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("mio68.lab.spring.confprop.validation.app-properties")
@Data
@Validated
public class AppProperties {

    @NotBlank
    private String username;

}
