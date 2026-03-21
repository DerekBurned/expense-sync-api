package org.example.expensestrack.services;

import org.example.expensestrack.Model.Expense;
import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.Model.Settings;
import org.example.expensestrack.repository.ExpenseRepository;
import org.example.expensestrack.repository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository  repository;
    private final SettingsRepository categoryRepository;

    public ExpenseService(ExpenseRepository repository, SettingsRepository categoryRepository) {
        this.repository         = repository;
        this.categoryRepository = categoryRepository;
    }

    public int syncOfflineExpenses(List<ExpenseDTO> dtos) {
        List<Expense> toSave = new ArrayList<>();

        for (ExpenseDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {

                Settings category = categoryRepository
                        .findByLocalId(dto.getCategoryLocalId())
                        .orElse(null);

                // Fall back to now() if the client sent no date
                LocalDateTime date = dto.getExpenseDate() != null
                        ? dto.getExpenseDate()
                        : LocalDateTime.now();

                toSave.add(new Expense(
                        dto.getLocalId(),
                        dto.getAmount(),
                        dto.getDescription(),
                        dto.getTransactionType(),
                        category,
                        dto.getUserId(),
                        date
                ));
            }
        }

        repository.saveAll(toSave);
        return toSave.size();
    }

    // deleteByLocalId() is now void — check existence first, then delete.
    public boolean deleteExpenseById(String localId) {
        if (!repository.existsByLocalId(localId)) return false;
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