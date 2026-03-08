package org.example.expensestrack.repository;

import org.example.expensestrack.Model.Settings;
import org.example.expensestrack.Model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
    List<Settings> findAllByUserId(String userId);
    List<Settings> findAllByUserIdAndTransactionType(String userId, TransactionType type);
    boolean existsByLocalId(String localId);
    Optional<Settings> findByLocalId(String localId);
    boolean deleteByLocalId(String localId);
}