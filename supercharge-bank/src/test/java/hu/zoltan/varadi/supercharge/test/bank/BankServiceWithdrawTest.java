package hu.zoltan.varadi.supercharge.test.bank;


import hu.zoltan.varadi.supercharge.test.bank.utils.TestApp;
import hu.zoltan.varadi.supercharge.test.bank.utils.TestUtils;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class BankServiceWithdrawTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private BankService bankService;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");

        bankService = TestApp.getBankService();
    }

    @Test(expected = BankRuntimeException.class)
    public void withdraw_with_negative_amount_should_throw_exception() {
        bankService.withdraw("1", -1);
    }

    @Test(expected = BankRuntimeException.class)
    public void withdraw_with_wrong_user_should_throw_exception() {
        bankService.withdraw("323424", 200);
    }


    @Test
    public void withdraw_first_money() {
        String accountNumber = "1";
        initUserPocket(accountNumber, 300);

        bankService.withdraw(accountNumber, 20);

        TestUtils.assertUserAccount(accountNumber)
                .inHistorySize(1)
                .outHistorySize(1)
                .hasBalance(280L);
    }

    @Test
    public void withdraw_two_times() {
        String accountNumber = "1";
        initUserPocket(accountNumber, 300);

        bankService.withdraw(accountNumber, 20);
        bankService.withdraw(accountNumber, 30);

        TestUtils.assertUserAccount(accountNumber)
                .inHistorySize(1)
                .outHistorySize(2)
                .hasBalance(250L);
    }


    @Test
    public void withdraw_all_money() {
        String accountNumber = "1";
        initUserPocket(accountNumber, 300);

        bankService.withdraw(accountNumber, 300);

        TestUtils.assertUserAccount(accountNumber)
                .inHistorySize(1)
                .outHistorySize(1)
                .hasBalance(0L);
    }

    @Test
    public void withdraw_too_much_money() {
        String accountNumber = "1";
        initUserPocket(accountNumber, 300);

        thrown.expect(BankRuntimeException.class);
        thrown.expectMessage("too.much.money");

        bankService.withdraw(accountNumber, 400);
    }


    void initUserPocket(String accountNumber, long amount) {

        TestUtils.initUserPocket(accountNumber, amount);
    }

}
