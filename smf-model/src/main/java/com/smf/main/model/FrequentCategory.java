package com.smf.main.model;

import com.smf.main.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@Getter
@Setter
public class FrequentCategory {
    private Category category;
}
