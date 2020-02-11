package me.duvu.tracking.repository;

import me.duvu.tracking.entities.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author beou on 8/1/17 03:11
 * @version 1.0
 */
@Repository
public interface AccountRepository extends Vd5CommonRepository<Account> {
    Account findAccountByAccountId(String accountId);
    Optional<Account> findByAccountId(String accountId);
}
