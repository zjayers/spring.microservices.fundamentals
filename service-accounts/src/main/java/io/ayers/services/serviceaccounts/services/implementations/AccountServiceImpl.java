package io.ayers.services.serviceaccounts.services.implementations;

import io.ayers.services.serviceaccounts.models.domain.AccountEntity;
import io.ayers.services.serviceaccounts.models.dto.AccountDto;
import io.ayers.services.serviceaccounts.repositories.AccountRepository;
import io.ayers.services.serviceaccounts.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl
        implements AccountService {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        String userId = UUID.randomUUID().toString();
        String encryptedPassword = bCryptPasswordEncoder.encode(accountDto.getPassword());

        accountDto.setUserId(userId);
        accountDto.setEncryptedPassword(encryptedPassword);

        AccountEntity accountEntity = modelMapper.map(accountDto, AccountEntity.class);
        accountRepository.save(accountEntity);

        return accountDto;
    }

    @Override
    public AccountDto getAccountDetailsByEmail(String email) {
        AccountEntity accountEntity = findByEmail(email);
        return modelMapper.map(accountEntity, AccountDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity accountEntity = findByEmail(email);

        return new User(accountEntity.getEmail(), accountEntity
                .getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }

    private AccountEntity findByEmail(String email) {
        AccountEntity accountEntity = accountRepository.findByEmail(email);

        if (accountEntity == null) throw new UsernameNotFoundException(String.format("Email not found: %s", email));

        return accountEntity;
    }
}
