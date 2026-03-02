package org.example.expensestrack.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseDTO {
    private String localId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime expenseDate;

    public String getLocalId() { return localId; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getExpenseDate() { return expenseDate; }
}