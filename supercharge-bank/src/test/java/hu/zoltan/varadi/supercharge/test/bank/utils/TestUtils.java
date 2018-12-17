package hu.zoltan.varadi.supercharge.test.bank.utils;

import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;
import hu.zoltan.varadi.supercharge.test.database.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestUtils {

    public static void createUser(String accountNumber) {

        UserEntity user = new UserEntity();
        user.setAccountNumber(accountNumber);
        TestApp.getUserRepository().persist(user);
    }

    public static void initUserPocket(String accountNumber, long amount) {

        TestApp.getBankService().deposit(accountNumber, amount);
    }

    public static UserAccountAssert assertUserAccount(String accountNumber) {
        return new UserAccountAssert(accountNumber);
    }

    public static UserHistoryBuilder userHistoryBuilder(String accountNumber) {
        return new UserHistoryBuilder(accountNumber);
    }

    public static class UserAccountAssert {
        private String accountNumber;

        private UserAccountAssert(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public UserAccountAssert inHistorySize(int size) {
            assertThat(history(TransactionHistoryEntity.TransactionDirection.IN), hasSize(size));
            return this;
        }

        public UserAccountAssert outHistorySize(int size) {
            assertThat(history(TransactionHistoryEntity.TransactionDirection.OUT), hasSize(size));
            return this;
        }

        public UserAccountAssert hasBalance(Long balance) {
            assertThat(TestApp.getBankService().getUserBalance(accountNumber), equalTo(balance));
            return this;
        }

        private List<TransactionHistoryEntity> history(TransactionHistoryEntity.TransactionDirection transactionDirection) {
            return TestApp.getTransactionHistory().findByAccountNumber(accountNumber)
                    .get()
                    .stream()
                    .filter(h -> h.getDirection() == transactionDirection)
                    .collect(Collectors.toList());
        }

    }

    public static class UserHistoryBuilder {

        private String accountNumber;

        private UserHistoryBuilder(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public UserHistoryBuilder setTime(LocalDateTime time) {
            TestApp.setTime(time);
            return this;
        }

        public UserHistoryBuilder deposit(Long amount) {
            TestApp.getBankService().deposit(accountNumber, amount);
            return this;
        }

        public UserHistoryBuilder withdraw(Long amount) {
            TestApp.getBankService().withdraw(accountNumber, amount);
            return this;
        }

        public UserHistoryBuilder transfer(String toAccount, Long amount) {
            TestApp.getBankService().transferMoney(accountNumber, toAccount, amount);
            return this;
        }

        public UserHistoryBuilder acceptMoney(String fromAccount, Long amount) {
            TestApp.getBankService().transferMoney(fromAccount, accountNumber, amount);
            return this;
        }
    }
}
