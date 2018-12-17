package hu.zoltan.varadi.supercharge.test.database.transaction;


import java.time.LocalDateTime;

public class TransactionHistoryEntity {

    private Long id;

    private long amount;

    private String ownerAccountNumber;

    private String transactionId;

    private TransactionDirection direction;

    private LocalDateTime transactionDate;

    public enum TransactionDirection {
        IN,
        OUT
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOwnerAccountNumber() {
        return ownerAccountNumber;
    }

    public void setOwnerAccountNumber(String ownerAccountNumber) {
        this.ownerAccountNumber = ownerAccountNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionHistoryEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", ownerAccountNumber='" + ownerAccountNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", direction=" + direction +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
