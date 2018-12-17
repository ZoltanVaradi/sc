package hu.zoltan.varadi.supercharge.test.bank;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;

import java.time.LocalDateTime;

public interface TransactionHistoryService {

    TransactionHistoryListResult findByAccountNumber(String accountNumber);

    TransactionHistoryListResult findByAccountNumberInDateRange(String ownerAccountNumber, LocalDateTime from, LocalDateTime till);

    TransactionHistoryListResult findByAccountNumberAndTransactionDirection(String ownerAccountNumber, TransactionHistoryEntity.TransactionDirection transactionDirection);
}
