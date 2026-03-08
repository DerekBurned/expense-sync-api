package org.example.expensestrack.controllers;

import org.example.expensestrack.Model.Expense;
import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // POST /api/expenses/sync
    @PostMapping("/sync")
    public ResponseEntity<String> syncExpenses(@RequestBody List<ExpenseDTO> expenses) {
        int savedCount = service.syncOfflineExpenses(expenses);
        return ResponseEntity.ok("Synced " + savedCount + " new expenses.");
    }

    // GET /api/expenses?userId=abc&sortBy=date
    @GetMapping
    public List<Expense> getAll(
            @RequestParam String userId,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return service.getAllExpenses(userId, sortBy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable String id) {
        boolean deleted = service.deleteExpenseById(id);
        return deleted
                ? ResponseEntity.ok("Deleted")
                : ResponseEntity.notFound().build();
    }
}