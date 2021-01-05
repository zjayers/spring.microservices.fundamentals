package io.ayers.services.serviceaccounts.models.dto;

import io.ayers.services.serviceaccounts.models.response.AlbumResponseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
    private static final long serialVersionUID = 3515135800161970167L;

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
    private List<AlbumResponseModel> albums;
}
