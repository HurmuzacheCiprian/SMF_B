package com.smf.main.domain;

import com.smf.main.Category;
import com.smf.main.entities.Expense;
import com.smf.main.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cipriach on 08.12.2015.
 */
@Transactional
public interface ExpensesDao extends CrudRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e where e.category = ?1")
    List<Expense> findByCategory(Category category);

    @Query(value = "SELECT e.category,count(*) as c from Expense e GROUP BY e.category ORDER BY c DESC", nativeQuery = true)
    List<Object[]> findMostFrequentExpenseCategory();

    List<Expense> findExpenseByUserEntity(UserEntity fk_user, Pageable pageable);

    @Query(value = "select e.id from expense e INNER JOIN user_entity u ON e.user_entity_id = u.id where u.user_name=?2 and e.id = ?1", nativeQuery = true)
    Long findExpensesByUser(Long fundId, String userName);

    @Query(value = "select e from Expense e where e.user_entity_id = ?1", nativeQuery = true)
    List<Expense> findExpensesByUserId(Long userId);

    @Query(value = "select sum(e.amount) from Expense e where e.user_entity_id = ?1 and e.is_computed = false", nativeQuery = true)
    Double findTotalAmountOfExpenses(Long userId);

    @Query(value = "select * from Expense e where e.user_entity_id = ?1 and extract(month from e.date) = ?3 and extract(day from e.date) = ?2", nativeQuery = true)
    List<Expense> findExpensesByDayAndMonth(Long userId, int dayOfMonth, int month);

    @Query(value = "select category,sum(amount) from expense where user_entity_id=?1 and extract(month from date) = ?2 group by(category)", nativeQuery = true)
    List<Object[]> findMonthlyCategoryReport(Long userId, int monthValue);

    @Modifying
    @Query(value = "update Expense set is_computed = true where user_entity_id = ?1", nativeQuery = true)
    void updateExpensesToComputed(Long userId);

    @Query(value = "select * from Expense e where e.user_entity_id = ?1 and extract(month from e.date) = ?2", nativeQuery = true)
    List<Expense> findExpensesByMonth(Long userId, int month);

}
