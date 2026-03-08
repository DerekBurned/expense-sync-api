package org.example.expensestrack.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses", indexes = {
        @Index(columnList = "expenseDate"),
        @Index(columnList = "localId", unique = true)
})
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String localId;

    private BigDecimal amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // INCOME or EXPENSE

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // ← now a full entity, not an enum

    private String userId;
    private LocalDateTime expenseDate;

    public Expense() {}

    public Expense(String localId, BigDecimal amount, String description,
                   TransactionType transactionType, Category category,
                   String userId, LocalDateTime expenseDate) {
        this.localId = localId;
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.category = category;
        this.userId = userId;
        this.expenseDate = expenseDate;
    }

    public Long getId() { return id; }
    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public LocalDateTime getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDateTime expenseDate) { this.expenseDate = expenseDate; }
}