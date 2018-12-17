package hu.zoltan.varadi.supercharge.test.bank;


import hu.zoltan.varadi.supercharge.test.bank.utils.TestApp;
import hu.zoltan.varadi.supercharge.test.bank.utils.TestUtils;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import org.junit.Before;
import org.junit.Test;


public class BankServiceDepositTest {

    private BankService bankService;

    private TransactionHistoryService transactionHistory;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");

        bankService = TestApp.getBankService();
        transactionHistory = TestApp.getTransactionHistory();
    }

    @Test(expected = BankRuntimeException.class)
    public void deposit_with_negative_amount_should_throw_exception() {
        bankService.deposit("1", -1);
    }

    @Test(expected = BankRuntimeException.class)
    public void deposit_with_wrong_user_should_throw_exception() {
        bankService.deposit("323424", 200);
    }


    @Test
    public void deposit_first_money() {
        String accountNumber = "1";

        bankService.deposit(accountNumber, 300);

        TestUtils.assertUserAccount(accountNumber)
                .inHistorySize(1)
                .outHistorySize(0)
                .hasBalance(300L);
    }

    @Test
    public void deposit_two_times() {
        String accountNumber = "1";

        bankService.deposit(accountNumber, 300);
        bankService.deposit(accountNumber, 20);

        TestUtils.assertUserAccount(accountNumber)
                .inHistorySize(2)
                .outHistorySize(0)
                .hasBalance(320L);
    }


}
