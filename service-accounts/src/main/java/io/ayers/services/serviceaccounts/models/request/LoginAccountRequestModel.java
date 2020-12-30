package io.ayers.services.serviceaccounts.models.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginAccountRequestModel {
    @NotNull(message = "Password cannot be empty.")
    @Size(min = 8, max = 24, message = "Password must be 8 or more characters.")
    private String password;

    @NotNull(message = "Email cannot be empty.")
    @Email
    private String email;
}
