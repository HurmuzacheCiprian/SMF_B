package com.smf.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cipriach on 21.12.2015.
 */
@Builder
@Getter
@Setter
public class EconomiesResponse {
    private List<EconomyResponse> economies;
    private int totalElements;
}
