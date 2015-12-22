package com.smf.main.model;

import com.smf.main.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by cipriach on 22.12.2015.
 */
@Builder
@Getter
@Setter
@ToString(of = {"category", "totalAmount","percentage"})
public class CategoryReport {
    private Category category;
    private Double totalAmount;
    private String percentage;
}
