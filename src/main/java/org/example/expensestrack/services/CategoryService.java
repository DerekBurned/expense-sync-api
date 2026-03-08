package org.example.expensestrack.services;

import org.example.expensestrack.Model.Category;
import org.example.expensestrack.Model.CategoryDTO;
import org.example.expensestrack.Model.TransactionType;
import org.example.expensestrack.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getCategories(String userId, TransactionType type) {
        return type != null
                ? repository.findAllByUserIdAndTransactionType(userId, type)
                : repository.findAllByUserId(userId);
    }

    // Sync offline-created categories (same pattern as expenses)
    public int syncCategories(List<CategoryDTO> dtos) {
        int count = 0;
        for (CategoryDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {
                repository.save(new Category(
                        dto.getLocalId(),
                        dto.getName(),
                        dto.getTransactionType(),
                        dto.getUserId()
                ));
                count++;
            }
        }
        return count;
    }

    public boolean deleteCategory(String localId) {
        return repository.deleteByLocalId(localId);
    }
}