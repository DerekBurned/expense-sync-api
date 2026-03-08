package org.example.expensestrack.repository;

import org.example.expensestrack.Model.Expense;
import org.example.expensestrack.Model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    boolean existsByLocalId(String localId);
    boolean deleteByLocalId(String localId);
    boolean deleteAllByExpenseDate(LocalDateTime expenseDate);

    // Sorted
    List<Expense> findAllByUserIdOrderByExpenseDateDesc(String userId);
    List<Expense> findAllByUserIdOrderByAmountDesc(String userId);
    List<Expense> findAllByUserIdAndTransactionType(String userId, TransactionType type);
}