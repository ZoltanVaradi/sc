package hu.zoltan.varadi.supercharge.test.bank.utils;


import hu.zoltan.varadi.supercharge.test.bank.BankService;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import org.junit.Before;
import org.junit.Test;


public class BankServiceUserBalanceTest {

    private BankService bankService;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");

        bankService = TestApp.getBankService();
    }

    @Test(expected = BankRuntimeException.class)
    public void get_balance_without_user_should_throw_exception() {
        bankService.getUserBalance("10");
    }


}
