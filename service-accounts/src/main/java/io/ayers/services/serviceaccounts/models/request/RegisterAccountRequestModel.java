package io.ayers.services.serviceaccounts.models.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterAccountRequestModel {
    @NotNull(message = "First name cannot be empty.")
    @Size(min = 2, message = "First name must not be less than 2 characters.")
    private String firstName;

    @NotNull(message = "Last name cannot be empty.")
    @Size(min = 2, message = "Last name must not be less than 2 characters.")
    private String lastName;

    @NotNull(message = "Password cannot be empty.")
    @Size(min = 8, max = 24, message = "Password must be 8 or more characters.")
    private String password;

    @NotNull(message = "Email cannot be empty.")
    @Email
    private String email;
}
