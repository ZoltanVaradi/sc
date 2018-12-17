package hu.zoltan.varadi.supercharge.test.bank;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionsRepository;
import hu.zoltan.varadi.supercharge.test.database.trlog.*;
import hu.zoltan.varadi.supercharge.test.database.user.UserEntity;
import hu.zoltan.varadi.supercharge.test.database.user.UserRepository;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import hu.zoltan.varadi.supercharge.test.util.RealTimeService;
import hu.zoltan.varadi.supercharge.test.util.TimeService;

import java.util.UUID;
import java.util.function.Supplier;

import static hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection.OUT;

public class BankServiceImpl implements BankService {

    private TimeService timeService = new RealTimeService();

    private UserRepository userRepository;

    private TransactionsRepository transactionsRepository;

    private TransactionLogRepository transactionLogRepository;

    public BankServiceImpl(UserRepository userRepository, TransactionsRepository transactionsRepository, TransactionLogRepository transactionLogRepository) {
        this.userRepository = userRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public void deposit(String accountNumber, long amount) {

        validateInputAmount(amount);

        UserEntity user = getUser(accountNumber);


        // meta
        DepositTransactionLogEntity depositTransactionLog = createTransactionLogEntity(DepositTransactionLogEntity::new);
        depositTransactionLog.setOwnerAccountNumber(user.getAccountNumber());

        transactionLogRepository.persist(depositTransactionLog);

        // tr
        createTransactionHistoryEntity(user, amount, TransactionDirection.IN, depositTransactionLog);

    }

    @Override
    public void withdraw(String accountNumber, long amount) {

        validateInputAmount(amount);

        UserEntity user = getUser(accountNumber);

        Long userBalance = getUserBalance(user.getAccountNumber());

        if (amount > userBalance) {
            throw new BankRuntimeException("too.much.money");
        }


        // meta
        WithdrawTransactionLogEntity withdrawTransactionLog = createTransactionLogEntity(WithdrawTransactionLogEntity::new);
        withdrawTransactionLog.setOwnerAccountNumber(user.getAccountNumber());

        transactionLogRepository.persist(withdrawTransactionLog);

        // create tr
        createTransactionHistoryEntity(user, amount, TransactionDirection.OUT, withdrawTransactionLog);

    }

    @Override
    public void transferMoney(String fromAccountNumber, String toAccountNumber, long amount) {
        validateInputAmount(amount);

        if (fromAccountNumber == null || toAccountNumber == null || fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException();
        }

        UserEntity fromUser = getUser(fromAccountNumber);
        UserEntity toUser = getUser(toAccountNumber);

        Long userBalance = getUserBalance(fromUser.getAccountNumber());

        if (amount > userBalance) {
            throw new BankRuntimeException("too.much.money");
        }

        // meta
        TransferTransactionLogEntity withdrawTransactionLog = createTransactionLogEntity(TransferTransactionLogEntity::new);
        withdrawTransactionLog.setFromAccountNumber(fromUser.getAccountNumber());
        withdrawTransactionLog.setToAccountNumber(toUser.getAccountNumber());

        transactionLogRepository.persist(withdrawTransactionLog);

        // create tr
        createTransactionHistoryEntity(fromUser, amount, TransactionDirection.OUT, withdrawTransactionLog);
        createTransactionHistoryEntity(toUser, amount, TransactionDirection.IN, withdrawTransactionLog);

    }

    @Override
    public Long getUserBalance(String accountNumber) {

        UserEntity user = getUser(accountNumber);

        return transactionsRepository.findByAccountNumber(user.getAccountNumber())
                .stream()
                .mapToLong(trHistory -> {
                    if (trHistory.getDirection() == OUT) {
                        return -1 * trHistory.getAmount();
                    } else {
                        return trHistory.getAmount();
                    }
                })
                .sum();
    }

    private TransactionHistoryEntity createTransactionHistoryEntity(UserEntity user, long amount, TransactionDirection direction, AbstractTransactionLogEntity transactionDate) {

        TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
        transactionHistoryEntity.setTransactionDate(transactionDate.getTransactionDate());
        transactionHistoryEntity.setTransactionId(transactionDate.getTransactionId());
        transactionHistoryEntity.setAmount(amount);
        transactionHistoryEntity.setOwnerAccountNumber(user.getAccountNumber());
        transactionHistoryEntity.setDirection(direction);

        transactionsRepository.persist(transactionHistoryEntity);

        return transactionHistoryEntity;

    }

    private <T extends AbstractTransactionLogEntity> T createTransactionLogEntity(Supplier<T> newInstance) {

        T depositTransactionLogEntity = newInstance.get();

        depositTransactionLogEntity.setTransactionId(createNewTrId());
        depositTransactionLogEntity.setTransactionDate(timeService.now());

        return depositTransactionLogEntity;
    }

    private String createNewTrId() {
        return UUID.randomUUID().toString();
    }

    private UserEntity getUser(String accountNumber) {
        return userRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankRuntimeException("user.not.found"));
    }


    private void validateInputAmount(long amount) {

        if (amount <= 0) {
            throw new BankRuntimeException();
        }
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
