package hu.zoltan.varadi.supercharge.test.bank;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionsRepository;
import hu.zoltan.varadi.supercharge.test.database.user.UserEntity;
import hu.zoltan.varadi.supercharge.test.database.user.UserRepository;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionHistoryImpl implements TransactionHistoryService {

    private UserRepository userRepository;

    private TransactionsRepository transactionsRepository;

    public TransactionHistoryImpl(UserRepository userRepository, TransactionsRepository transactionsRepository) {
        this.userRepository = userRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public TransactionHistoryListResult findByAccountNumber(String accountNumber) {
        UserEntity user = getUser(accountNumber);

        List<TransactionHistoryEntity> byAccountNumber = transactionsRepository.findByAccountNumber(user.getAccountNumber());
        return new TransactionHistoryListResult(byAccountNumber);
    }

    @Override
    public TransactionHistoryListResult findByAccountNumberInDateRange(String ownerAccountNumber, LocalDateTime from, LocalDateTime till) {

        UserEntity user = getUser(ownerAccountNumber);

        if (from == null || till == null || from.isAfter(till)) {
            throw new IllegalArgumentException();
        }

        List<TransactionHistoryEntity> byAccountNumberInDateRange = transactionsRepository.findByAccountNumberInDateRange(user.getAccountNumber(), from, till);
        return new TransactionHistoryListResult(byAccountNumberInDateRange);
    }

    @Override
    public TransactionHistoryListResult findByAccountNumberAndTransactionDirection(String ownerAccountNumber, TransactionDirection transactionDirection) {

        if (transactionDirection == null) {
            throw new IllegalArgumentException();
        }

        UserEntity user = getUser(ownerAccountNumber);

        List<TransactionHistoryEntity> byAccountNumberAndTransactionDirection = transactionsRepository.findByAccountNumberAndTransactionDirection(user.getAccountNumber(), transactionDirection);
        return new TransactionHistoryListResult(byAccountNumberAndTransactionDirection);
    }


    private UserEntity getUser(String accountNumber) {
        return userRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankRuntimeException("user.not.found"));
    }

}
