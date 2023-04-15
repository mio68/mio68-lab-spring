package mio68.lab.spring.data.jdbc.entity;

import lombok.*;

@Value
@Builder
public class CommonInfo {

    private final String code;
    private final String name;
    private final String description;

}
