package org.example.expensestrack.controllers;

import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.Model.ExpenseResponseDTO;
import org.example.expensestrack.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Fixes applied:
 * 1. getAll() now returns List<ExpenseResponseDTO> instead of List<Expense>.
 *    Returning the raw JPA entity serialised the nested Settings object as a
 *    JSON object under the key "category", which Android's flat TransactionDTO
 *    (with field "categoryLocalId") couldn't deserialise — every category came
 *    back as DEFAULT/OTHER.
 *
 * 2. deleteExpense() — no change needed; service.deleteExpenseById() already
 *    returns boolean after the ExpenseService fix.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncExpenses(@RequestBody List<ExpenseDTO> expenses) {
        int savedCount = service.syncOfflineExpenses(expenses);
        return ResponseEntity.ok("Synced " + savedCount + " new expenses.");
    }

    /**
     * FIX: project to ExpenseResponseDTO so the JSON shape matches Android's
     * flat TransactionDTO (fields: localId, amount, description, transactionType,
     * categoryLocalId, date).
     */
    @GetMapping
    public List<ExpenseResponseDTO> getAll(
            @RequestParam String userId,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return service.getAllExpenses(userId, sortBy)
                .stream()
                .map(ExpenseResponseDTO::from)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable String id) {
        boolean deleted = service.deleteExpenseById(id);
        return deleted
                ? ResponseEntity.ok("Deleted")
                : ResponseEntity.notFound().build();
    }
}