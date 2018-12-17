package hu.zoltan.varadi.supercharge.test.database.inmemoryimpl;

import hu.zoltan.varadi.supercharge.test.database.trlog.AbstractTransactionLogEntity;
import hu.zoltan.varadi.supercharge.test.database.trlog.TransactionLogRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InmemoryTransactionLogRepository implements TransactionLogRepository {

    private List<AbstractTransactionLogEntity> db = new LinkedList<>();

    @Override
    public List<AbstractTransactionLogEntity> findByTrId(String trId) {

        return db.stream().filter(t -> t.getTransactionId().equals(trId)).collect(Collectors.toList());
    }

    @Override
    public void persist(AbstractTransactionLogEntity transactionLogEntity) {
        if (transactionLogEntity.getId() != null) {
            throw new IllegalStateException("id must be null");
        }

        transactionLogEntity.setId(Math.abs(new Random().nextLong()));

        db.add(transactionLogEntity);
    }
}
