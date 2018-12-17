package hu.zoltan.varadi.supercharge.test.database.transaction;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepository {

    List<TransactionHistoryEntity> findByAccountNumber(String accountNumber);

    List<TransactionHistoryEntity> findByAccountNumberInDateRange(String ownerAccountNumber, LocalDateTime from, LocalDateTime till);

    List<TransactionHistoryEntity> findByAccountNumberAndTransactionDirection(String ownerAccountNumber, TransactionDirection transactionDirection);

    void persist(TransactionHistoryEntity transactionEntity);
}
