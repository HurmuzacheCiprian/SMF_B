package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by cipriach on 07.12.2015.
 */
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private String userName;
    private String password;
}
