package hu.zoltan.varadi.supercharge.test.database.inmemoryimpl;

import hu.zoltan.varadi.supercharge.test.database.user.UserEntity;
import hu.zoltan.varadi.supercharge.test.database.user.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InmemoryUserRepository implements UserRepository {

    private List<UserEntity> db = new LinkedList<>();

    @Override
    public Optional<UserEntity> findByAccountNumber(String accountNumber) {
        return db.stream().filter(u -> u.getAccountNumber().equals(accountNumber)).findFirst();
    }


    @Override
    public void persist(UserEntity user) {
        if (user.getId() != null) {
            throw new IllegalStateException("id most be null");
        }

        user.setId(UUID.randomUUID().toString());

        db.add(user);
    }
}
