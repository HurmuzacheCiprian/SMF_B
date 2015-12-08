package com.smf.main;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by cipriach on 07.12.2015.
 */
@Builder
@Getter
@Setter
public class UserResponse implements Serializable{
    private boolean isOk;
}
