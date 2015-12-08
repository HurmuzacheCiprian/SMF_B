package com.smf.main.model;

import lombok.*;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundRegistration {
    private String fundName;
    private Long fundAmount;
}
