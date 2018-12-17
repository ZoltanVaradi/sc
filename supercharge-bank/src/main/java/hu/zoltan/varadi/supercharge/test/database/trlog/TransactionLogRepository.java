package hu.zoltan.varadi.supercharge.test.database.trlog;

import java.util.List;

public interface TransactionLogRepository {

    List<AbstractTransactionLogEntity> findByTrId(String trId);

    void persist(AbstractTransactionLogEntity transactionLogEntity);
}
