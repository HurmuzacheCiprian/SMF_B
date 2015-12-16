package com.smf.main.domain;

import com.smf.main.entities.Fund;
import com.smf.main.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cipriach on 08.12.2015.
 */
@Transactional
public interface FundDao extends JpaRepository<Fund, Long> {

    List<Fund> findByuserEntity(UserEntity fk_user, Pageable pageable);

}
