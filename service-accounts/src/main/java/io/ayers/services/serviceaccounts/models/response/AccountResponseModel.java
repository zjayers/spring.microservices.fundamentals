package io.ayers.services.serviceaccounts.models.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AlbumResponseModel> albums;
}
