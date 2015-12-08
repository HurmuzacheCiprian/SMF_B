package com.smf.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by cipriach on 08.12.2015.
 */
@Getter
@Setter
@Builder
public class FundResponse {

    private String fundName;
    private Long amount;
    private Date createdDate;

}
