package hu.zoltan.varadi.supercharge.test.bank;


import hu.zoltan.varadi.supercharge.test.bank.utils.TestApp;
import hu.zoltan.varadi.supercharge.test.bank.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;


public class BankServiceTransferMoneyTest {

    private BankService bankService;

    private TransactionHistoryService transactionHistory;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");
        TestUtils.createUser("2");

        bankService = TestApp.getBankService();
        transactionHistory = TestApp.getTransactionHistory();
    }

    @Test()
    public void deposit_with_negative_amount() {

        initUserPocket("1", 200);

        bankService.transferMoney("1", "2", 30);

        // user1

        TestUtils.assertUserAccount("1")
                .inHistorySize(1)
                .outHistorySize(1)
                .hasBalance(170L);

        // user2

        TestUtils.assertUserAccount("2")
                .inHistorySize(1)
                .outHistorySize(0)
                .hasBalance(30L);
    }


    void initUserPocket(String accountNumber, long amount) {

        TestUtils.initUserPocket(accountNumber, amount);
    }


}
