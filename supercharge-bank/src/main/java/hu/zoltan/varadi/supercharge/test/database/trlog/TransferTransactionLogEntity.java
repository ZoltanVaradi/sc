package hu.zoltan.varadi.supercharge.test.database.trlog;


public class TransferTransactionLogEntity extends AbstractTransactionLogEntity {
    private String toAccountNumber;

    private String fromAccountNumber;

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }
}
