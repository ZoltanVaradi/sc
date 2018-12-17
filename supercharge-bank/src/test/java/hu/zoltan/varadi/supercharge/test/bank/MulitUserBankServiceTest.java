package hu.zoltan.varadi.supercharge.test.bank;


import hu.zoltan.varadi.supercharge.test.bank.utils.TestApp;
import hu.zoltan.varadi.supercharge.test.bank.utils.TestUtils;
import hu.zoltan.varadi.supercharge.test.error.BankRuntimeException;
import org.junit.Before;
import org.junit.Test;


public class MulitUserBankServiceTest {

    private BankService bankService;

    private TransactionHistoryService transactionHistory;

    @Before
    public void before() {
        TestApp.init();

        TestUtils.createUser("1");
        TestUtils.createUser("2");
        TestUtils.createUser("3");

        bankService = TestApp.getBankService();
        transactionHistory = TestApp.getTransactionHistory();
    }


    @Test
    public void use_bank_things() {

        TestUtils.initUserPocket("3", 3000L);

        //init user 1
        TestUtils.userHistoryBuilder("1")
                .deposit(300L)
                .deposit(300L)
                .withdraw(20L)
                .deposit(400L)
                .transfer("2", 30L)
                .acceptMoney("3", 10L)
                .withdraw(200L);

        //init user 2
        TestUtils.userHistoryBuilder("2")
                .deposit(3000L)
                .transfer("3", 1000L)
                .withdraw(200L);


        //asset users
        TestUtils.assertUserAccount("1")
                .inHistorySize(4)
                .outHistorySize(3)
                .hasBalance(760L);

        TestUtils.assertUserAccount("2")
                .inHistorySize(2)
                .outHistorySize(2)
                .hasBalance(1830L);

        TestUtils.assertUserAccount("3")
                .inHistorySize(2)
                .outHistorySize(1)
                .hasBalance(3990L);
    }



    @Test
    public void get_back_money() {

        //init user 1
        TestUtils.userHistoryBuilder("1")
                .deposit(300L)
                .withdraw(300L);

        //init user 2
        TestUtils.userHistoryBuilder("2")
                .deposit(3000L)
                .withdraw(3000L);


        //asset users
        TestUtils.assertUserAccount("1")
                .inHistorySize(1)
                .outHistorySize(1)
                .hasBalance(0L);

        TestUtils.assertUserAccount("2")
                .inHistorySize(1)
                .outHistorySize(1)
                .hasBalance(0L);
    }


}
