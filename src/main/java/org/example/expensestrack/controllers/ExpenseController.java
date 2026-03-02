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

    @PostMapping("/sync")
    public ResponseEntity<String> syncExpenses(@RequestBody List<ExpenseDTO> expenses) {
        int savedCount = service.syncOfflineExpenses(expenses);
        return ResponseEntity.ok("Synced successfully. Added " + savedCount + " new expenses.");
    }

    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }
}