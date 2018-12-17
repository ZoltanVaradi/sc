package hu.zoltan.varadi.supercharge.test.bank;


import hu.zoltan.varadi.supercharge.test.bank.utils.TestApp;
import hu.zoltan.varadi.supercharge.test.bank.utils.TestUtils;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity;
import hu.zoltan.varadi.supercharge.test.database.transaction.TransactionHistoryEntity.TransactionDirection;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class TransferHistoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private BankService bankService;

    private TransactionHistoryService transactionHistory;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");
        TestUtils.createUser("2");
        TestUtils.createUser("3");
        TestUtils.initUserPocket("3", 30000L);

        transactionHistory = TestApp.getTransactionHistory();
        bankService = TestApp.getBankService();
    }


    @Test
    public void transfer_money_without_user_should_throw_exception() {
        TestUtils.initUserPocket("1", 30000L);

        thrown.expect(BankRuntimeException.class);
        bankService.transferMoney("100", "1", 20);
    }

    @Test
    public void transfer_money_without_to_account_should_throw_exception() {
        TestUtils.initUserPocket("1", 30000L);

        thrown.expect(BankRuntimeException.class);
        bankService.transferMoney("1", "100", 20);
    }

    @Test
    public void transfer_zero_money_should_throw_exception() {
        TestUtils.initUserPocket("1", 30000L);

        thrown.expect(BankRuntimeException.class);
        bankService.transferMoney("1", "100", 0);
    }

    @Test
    public void transfer_money_to_same_user_should_throw_exception() {
        TestUtils.initUserPocket("1", 30000L);

        thrown.expect(IllegalArgumentException.class);
        bankService.transferMoney("1", "1", 20);
    }

    @Test()
    public void assert_and_print_history() {

        //init user history
        TestUtils.userHistoryBuilder("1")
                .deposit(300L)
                .deposit(300L)
                .withdraw(20L)
                .deposit(400L)
                .transfer("2", 30L)
                .acceptMoney("3", 10L)
                .withdraw(200L);


        //assert
        TestUtils.assertUserAccount("1")
                .inHistorySize(4)
                .outHistorySize(3)
                .hasBalance(760L);


        //print
        printHistory("1");
        printHistory("2");
        printHistory("3");

    }

    @Test()
    public void list_in_money() {

        //init user history
        TestUtils.userHistoryBuilder("1")
                .deposit(300L)
                .deposit(300L)
                .withdraw(20L)
                .deposit(400L)
                .transfer("2", 30L)
                .acceptMoney("3", 10L)
                .withdraw(200L);


        TransactionHistoryListResult inHistories = transactionHistory.findByAccountNumberAndTransactionDirection("1", TransactionDirection.IN);

        assertTransactionDirection(inHistories.get(), TransactionDirection.IN);
        printHistory("1", inHistories);
    }

    @Test()
    public void list_out_money() {

        //init user history
        TestUtils.userHistoryBuilder("1")
                .deposit(300L)
                .deposit(300L)
                .withdraw(20L)
                .deposit(400L)
                .transfer("2", 30L)
                .acceptMoney("3", 10L)
                .withdraw(200L);

        TransactionHistoryListResult outHistories = transactionHistory.findByAccountNumberAndTransactionDirection("1", TransactionDirection.OUT);

        assertTransactionDirection(outHistories.get(), TransactionDirection.OUT);
        printHistory("1", outHistories);
    }

    @Test()
    public void find_transaction_between_dates() {

        //init user history
        TestUtils.userHistoryBuilder("1")
                .setTime(now().minusDays(20)).deposit(300L)
                .setTime(now().minusDays(19)).deposit(300L)
                .setTime(now().minusDays(10)).withdraw(20L)
                .setTime(now().minusDays(6)).deposit(400L)
                .setTime(now().minusDays(3)).transfer("2", 30L)
                .setTime(now().minusDays(2)).acceptMoney("3", 10L)
                .setTime(now().minusDays(2)).withdraw(200L);


        LocalDateTime from = now().minusDays(11);
        LocalDateTime till = now().minusDays(3);

        TransactionHistoryListResult byAccountNumberInDateRange = transactionHistory.findByAccountNumberInDateRange("1", from, till);

        assertThat(byAccountNumberInDateRange.get(), hasSize(3));
        byAccountNumberInDateRange.print();
    }


    private void assertTransactionDirection(List<TransactionHistoryEntity> historyEntities, TransactionDirection expected) {
        historyEntities.forEach(transactionHistoryEntity -> {
            assertThat(transactionHistoryEntity.getDirection(), equalTo(expected));
        });
    }

    private void printHistory(String accountNumber) {
        printHistory(accountNumber, TestApp.getTransactionHistory().findByAccountNumber(accountNumber));
        System.out.println(String.format("balance: %d", TestApp.getBankService().getUserBalance(accountNumber)));
    }

    private void printHistory(String accountNumber, TransactionHistoryListResult historyEntities) {
        System.out.println(String.format("accountNumber: %s", accountNumber));
        historyEntities.print();
    }
}
