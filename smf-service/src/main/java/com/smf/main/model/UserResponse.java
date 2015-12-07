package com.smf.main.model;

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
