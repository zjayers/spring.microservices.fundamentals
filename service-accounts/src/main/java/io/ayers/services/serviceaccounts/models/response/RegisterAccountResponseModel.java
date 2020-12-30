package io.ayers.services.serviceaccounts.models.response;

import lombok.Data;

@Data
public class RegisterAccountResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
