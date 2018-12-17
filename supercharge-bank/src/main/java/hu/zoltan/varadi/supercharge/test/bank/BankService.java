package hu.zoltan.varadi.supercharge.test.bank;

public interface BankService {

    void deposit(String accountNumber, long amount);

    void withdraw(String accountNumber, long amount);

    void transferMoney(String fromAccountNumber, String toAccountNumber, long amount);

    Long getUserBalance(String accountNumber);
}
