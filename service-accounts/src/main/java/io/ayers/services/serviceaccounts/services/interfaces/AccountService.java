package io.ayers.services.serviceaccounts.services.interfaces;

import io.ayers.services.serviceaccounts.models.dto.AccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountDetailsByEmail(String email);
}
