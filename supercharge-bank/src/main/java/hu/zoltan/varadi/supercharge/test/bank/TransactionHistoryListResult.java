package hu.zoltan.varadi.supercharge.test.bank;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;

import java.util.List;
import java.util.function.Consumer;

public class TransactionHistoryListResult {

    private final List<TransactionHistoryEntity> transactionHistoryEntities;

    public TransactionHistoryListResult(List<TransactionHistoryEntity> transactionHistoryEntities) {
        this.transactionHistoryEntities = transactionHistoryEntities;
    }

    public List<TransactionHistoryEntity> get() {
        return transactionHistoryEntities;
    }

    public void print() {
        transactionHistoryEntities.forEach(System.out::println);
    }
}
