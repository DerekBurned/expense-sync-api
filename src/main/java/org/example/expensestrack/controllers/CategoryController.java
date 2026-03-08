package org.example.expensestrack.controllers;

import org.example.expensestrack.Model.Category;
import org.example.expensestrack.Model.CategoryDTO;
import org.example.expensestrack.Model.TransactionType;
import org.example.expensestrack.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    // GET /api/categories?userId=abc
    // GET /api/categories?userId=abc&type=EXPENSE
    @GetMapping
    public List<Category> getCategories(
            @RequestParam String userId,
            @RequestParam(required = false) TransactionType type
    ) {
        return service.getCategories(userId, type);
    }

    // POST /api/categories/sync  ← bulk sync offline-created categories
    @PostMapping("/sync")
    public ResponseEntity<String> syncCategories(@RequestBody List<CategoryDTO> categories) {
        int count = service.syncCategories(categories);
        return ResponseEntity.ok("Synced " + count + " new categories.");
    }

    @DeleteMapping("/{localId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String localId) {
        boolean deleted = service.deleteCategory(localId);
        return deleted
                ? ResponseEntity.ok("Deleted")
                : ResponseEntity.notFound().build();
    }
}