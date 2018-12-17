package hu.zoltan.varadi.supercharge.test.database.inmemoryimpl;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionsRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InmemoryTransactionsRepository implements TransactionsRepository {

    private List<TransactionHistoryEntity> db = new LinkedList<>();


    @Override
    public List<TransactionHistoryEntity> findByAccountNumber(String ownerAccountNumber) {
        return db.stream()
                .filter(historyEntity -> historyEntity.getOwnerAccountNumber().equals(ownerAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistoryEntity> findByAccountNumberAndTransactionDirection(String ownerAccountNumber, TransactionDirection transactionDirection) {
        return db.stream()
                .filter(historyEntity -> historyEntity.getOwnerAccountNumber().equals(ownerAccountNumber))
                .filter(historyEntity -> historyEntity.getDirection() == transactionDirection)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistoryEntity> findByAccountNumberInDateRange(String ownerAccountNumber, LocalDateTime from, LocalDateTime till) {
        return db.stream()
                .filter(history -> history.getOwnerAccountNumber().equals(ownerAccountNumber))
                .filter(history -> history.getTransactionDate().isEqual(from) || history.getTransactionDate().isAfter(from))
                .filter(history -> history.getTransactionDate().isEqual(till) || history.getTransactionDate().isBefore(till))
                .collect(Collectors.toList());
    }


    @Override
    public void persist(TransactionHistoryEntity transactionEntity) {
        if (transactionEntity.getId() != null) {
            throw new IllegalStateException("id must be null");
        }

        transactionEntity.setId(Math.abs(new Random().nextLong()));

        db.add(transactionEntity);

    }
}
