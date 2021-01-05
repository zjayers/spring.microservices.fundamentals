package io.ayers.services.serviceaccounts.repositories;

import io.ayers.services.serviceaccounts.models.domain.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    AccountEntity findByEmail(String email);
    AccountEntity findByUserId(String userId);
}