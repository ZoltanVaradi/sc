package hu.zoltan.varadi.supercharge.test.bank.utils;

import hu.zoltan.varadi.supercharge.test.bank.BankService;
import hu.zoltan.varadi.supercharge.test.bank.BankServiceImpl;
import hu.zoltan.varadi.supercharge.test.bank.TransactionHistoryImpl;
import hu.zoltan.varadi.supercharge.test.bank.TransactionHistoryService;
import hu.zoltan.varadi.supercharge.test.database.inmemoryimpl.InmemoryTransactionLogRepository;
import hu.zoltan.varadi.supercharge.test.database.inmemoryimpl.InmemoryTransactionsRepository;
import hu.zoltan.varadi.supercharge.test.database.inmemoryimpl.InmemoryUserRepository;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionsRepository;
import hu.zoltan.varadi.supercharge.test.database.trlog.TransactionLogRepository;
import hu.zoltan.varadi.supercharge.test.database.user.UserRepository;

import java.time.LocalDateTime;

public class TestApp {

    private static UserRepository userRepository;
    private static MockTimeService mockTimeService;
    private static TransactionsRepository transactionsRepository;
    private static TransactionLogRepository transactionLogRepository;

    private static BankServiceImpl bankService;
    private static TransactionHistoryService transactionHistory;

    static {
        init();
    }

    public static BankService getBankService() {
        return bankService;
    }

    public static TransactionHistoryService getTransactionHistory() {
        return transactionHistory;
    }

    public static void setTime(LocalDateTime time) {
        mockTimeService.mockTime = time;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static void init() {

        mockTimeService = new MockTimeService();
        userRepository = new InmemoryUserRepository();
        transactionsRepository = new InmemoryTransactionsRepository();
        transactionLogRepository = new InmemoryTransactionLogRepository();

        bankService = new BankServiceImpl(userRepository, transactionsRepository, transactionLogRepository);
        transactionHistory = new TransactionHistoryImpl(userRepository, transactionsRepository);

        //mock time
        bankService.setTimeService(mockTimeService);
    }
}
