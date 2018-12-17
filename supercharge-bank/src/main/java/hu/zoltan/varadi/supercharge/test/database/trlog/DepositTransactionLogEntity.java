package hu.zoltan.varadi.supercharge.test.database.trlog;

public class DepositTransactionLogEntity extends AbstractTransactionLogEntity {

    private String ownerAccountNumber;

    public String getOwnerAccountNumber() {
        return ownerAccountNumber;
    }

    public void setOwnerAccountNumber(String ownerAccountNumber) {
        this.ownerAccountNumber = ownerAccountNumber;
    }
}
