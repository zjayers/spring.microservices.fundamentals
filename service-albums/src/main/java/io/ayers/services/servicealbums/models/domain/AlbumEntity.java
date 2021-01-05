package io.ayers.services.servicealbums.models.domain;

import lombok.Data;

@Data
public class AlbumEntity {
    private long id;
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
