package com.smf.main.domain;

import com.smf.main.entities.Fund;
import com.smf.main.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cipriach on 08.12.2015.
 */
@Transactional
public interface FundDao extends JpaRepository<Fund, Long> {

    List<Fund> findByuserEntity(UserEntity fk_user, Pageable pageable);

    @Query(value = "select f.id from fund f INNER JOIN user_entity u ON f.fk_user = u.id where u.user_name=?2 and f.id = ?1", nativeQuery = true)
    Long findFundByUser(Long fundId, String userName);

    @Query(value = "select sum(f.amount) from fund f where f.fk_user = ?1", nativeQuery = true)
    Double findTotalAmountForUser(Long userId);
}
