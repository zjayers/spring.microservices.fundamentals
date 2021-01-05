package io.ayers.services.serviceaccounts.controllers;

import io.ayers.services.serviceaccounts.models.dto.AccountDto;
import io.ayers.services.serviceaccounts.models.request.RegisterAccountRequestModel;
import io.ayers.services.serviceaccounts.models.response.AccountResponseModel;
import io.ayers.services.serviceaccounts.models.response.RegisterAccountResponseModel;
import io.ayers.services.serviceaccounts.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @PostMapping(
            path = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<RegisterAccountResponseModel> createUser(@Valid @RequestBody RegisterAccountRequestModel accountRequestModel) {
        AccountDto accountDto = modelMapper.map(accountRequestModel, AccountDto.class);
        AccountDto savedAccountDto = accountService.createAccount(accountDto);

        RegisterAccountResponseModel registerAccountResponseModel = modelMapper.map(savedAccountDto,
                RegisterAccountResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerAccountResponseModel);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AccountResponseModel> getAccountDetails(@PathVariable("userId") String userId) {

        AccountDto accountDto = accountService.getAccountDetailsByUserId(userId);
        AccountResponseModel accountResponseModel = modelMapper.map(accountDto, AccountResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(accountResponseModel);
    }
}
