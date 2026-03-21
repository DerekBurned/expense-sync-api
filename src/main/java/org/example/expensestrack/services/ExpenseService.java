package org.example.expensestrack.services;

import org.example.expensestrack.Model.Expense;
import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.Model.Settings;
import org.example.expensestrack.repository.ExpenseRepository;
import org.example.expensestrack.repository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Fix: deleteExpenseById() previously returned repository.deleteByLocalId()
 * which is now void (with @Modifying). Use existsByLocalId() first.
 */
@Service
public class ExpenseService {

    private final ExpenseRepository    repository;
    private final SettingsRepository   categoryRepository;

    public ExpenseService(ExpenseRepository repository, SettingsRepository categoryRepository) {
        this.repository         = repository;
        this.categoryRepository = categoryRepository;
    }

    public int syncOfflineExpenses(List<ExpenseDTO> dtos) {
        List<Expense> newExpenses = new ArrayList<>();

        for (ExpenseDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {
                Settings settings = categoryRepository
                        .findByLocalId(dto.getCategoryLocalId())
                        .orElse(null);

                newExpenses.add(new Expense(
                        dto.getLocalId(),
                        dto.getAmount(),
                        dto.getDescription(),
                        dto.getTransactionType(),
                        settings,
                        dto.getUserId(),
                        dto.getExpenseDate()
                ));
            }
        }

        repository.saveAll(newExpenses);
        return newExpenses.size();
    }

    /**
     * FIX: check existence before deleting; repository method is now void.
     */
    public boolean deleteExpenseById(String localId) {
        if (!repository.existsByLocalId(localId)) {
            return false;
        }
        repository.deleteByLocalId(localId);
        return true;
    }

    public List<Expense> getAllExpenses(String userId, String sortBy) {
        return switch (sortBy) {
            case "amount" -> repository.findAllByUserIdOrderByAmountDesc(userId);
            default       -> repository.findAllByUserIdOrderByExpenseDateDesc(userId);
        };
    }
}