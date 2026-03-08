package org.example.expensestrack.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String localId; // Android-generated UUID

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType; // INCOME or EXPENSE

    @Column(nullable = false)
    private String userId;

    public Category() {}

    public Category(String localId, String name, TransactionType transactionType, String userId) {
        this.localId = localId;
        this.name = name;
        this.transactionType = transactionType;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}