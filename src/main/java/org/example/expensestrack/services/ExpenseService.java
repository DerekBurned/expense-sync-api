package org.example.expensestrack.services;


import org.example.expensestrack.Model.Expense;
import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public int syncOfflineExpenses(List<ExpenseDTO> dtos) {
        List<Expense> newExpenses = new ArrayList<>();

        for (ExpenseDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {
                // Using the new constructor
                Expense expense = new Expense(
                    dto.getLocalId(),
                    dto.getAmount(),
                    dto.getDescription(),
                    dto.getExpenseDate()
                );
                newExpenses.add(expense);
            }
        }

        repository.saveAll(newExpenses);

        return newExpenses.size();
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }
}
