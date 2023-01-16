package mio68.lab.spring.webasync.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    private String uuid;
    private String text;

    public static Msg create(String txt) {
        return new Msg(UUID.randomUUID().toString(), txt);
    }
}
