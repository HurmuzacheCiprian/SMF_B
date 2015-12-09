package com.smf.main.domain;

import com.smf.main.Category;
import com.smf.main.entities.Expense;
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
}
