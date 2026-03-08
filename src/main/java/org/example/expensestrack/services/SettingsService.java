package org.example.expensestrack.services;

import org.example.expensestrack.Model.Settings;
import org.example.expensestrack.Model.SettingsDTO;
import org.example.expensestrack.Model.TransactionType;
import org.example.expensestrack.repository.SettingsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SettingsService {

    private final SettingsRepository repository;

    public SettingsService(SettingsRepository repository) {
        this.repository = repository;
    }

    public List<Settings> getCategories(String userId, TransactionType type) {
        return type != null
                ? repository.findAllByUserIdAndTransactionType(userId, type)
                : repository.findAllByUserId(userId);
    }

    // Sync offline-created categories (same pattern as expenses)
    public int syncCategories(List<SettingsDTO> dtos) {
        int count = 0;
        for (SettingsDTO dto : dtos) {
            if (!repository.existsByLocalId(dto.getLocalId())) {

                count++;
            }
        }
        return count;
    }

    public boolean deleteCategory(String localId) {
        return repository.deleteByLocalId(localId);
    }
}