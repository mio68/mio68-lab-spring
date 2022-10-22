package mio68.lab.spring.test.component.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    private int id;
    private String firstName;
    private String lastName;

}
