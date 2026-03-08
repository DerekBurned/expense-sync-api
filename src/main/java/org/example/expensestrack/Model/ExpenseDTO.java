package org.example.expensestrack.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseDTO {
    private String localId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private String categoryLocalId; // ← reference by localId, not the full object
    private String userId;
    private LocalDateTime expenseDate;

    public String getLocalId() { return localId; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public TransactionType getTransactionType() { return transactionType; }
    public String getCategoryLocalId() { return categoryLocalId; }
    public String getUserId() { return userId; }
    public LocalDateTime getExpenseDate() { return expenseDate; }
}