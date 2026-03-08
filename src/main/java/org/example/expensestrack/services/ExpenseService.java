package org.example.expensestrack.services;

import org.example.expensestrack.Model.*;
import org.example.expensestrack.repository.SettingsRepository;
import org.example.expensestrack.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final SettingsRepository categoryRepository;

    public ExpenseService(ExpenseRepository repository, SettingsRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public int syncOfflineExpenses(List<ExpenseDTO> dtos) {
        List<Expense> newExpenses = new ArrayList<>();

        for (ExpenseDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {

                // Look up the category by its Android-generated localId
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

    public boolean deleteExpenseById(String id) {
        return repository.deleteByLocalId(id);
    }

    public List<Expense> getAllExpenses(String userId, String sortBy) {
        return switch (sortBy) {
            case "amount" -> repository.findAllByUserIdOrderByAmountDesc(userId);
            default       -> repository.findAllByUserIdOrderByExpenseDateDesc(userId);
        };
    }
}