package com.smf.main.domain;

import com.smf.main.entities.Fund;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cipriach on 08.12.2015.
 */
@Transactional
public interface FundDao extends CrudRepository<Fund, Long> {
    Fund findByName(String name);

}
