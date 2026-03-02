package org.example.expensestrack.repository;

import org.example.expensestrack.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Spring magically translates this method name into:
    // SELECT COUNT(*) > 0 FROM expenses WHERE local_id = ?
    boolean existsByLocalId(String localId);
}