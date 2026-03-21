package org.example.expensestrack.controllers;

import org.example.expensestrack.Model.ExpenseDTO;
import org.example.expensestrack.Model.ExpenseResponseDTO;
import org.example.expensestrack.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncExpenses(@RequestBody List<ExpenseDTO> expenses) {
        int saved = service.syncOfflineExpenses(expenses);
        return ResponseEntity.ok("Synced " + saved + " new expenses.");
    }

    /**
     * userId is now optional (required = false, default = "default-user").
     *
     * Android sends no userId at the moment. Making it optional means the
     * endpoint returns 200 with results instead of 400 Bad Request, so the
     * app can actually display data. Replace the default with real auth later.
     */
    @GetMapping
    public List<ExpenseResponseDTO> getAll(
            @RequestParam(required = false, defaultValue = "default-user") String userId,
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