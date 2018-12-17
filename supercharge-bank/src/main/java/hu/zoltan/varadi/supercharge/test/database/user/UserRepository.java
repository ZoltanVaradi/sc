package hu.zoltan.varadi.supercharge.test.database.user;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByAccountNumber(String accountNumber);

    void persist(UserEntity user);
}
