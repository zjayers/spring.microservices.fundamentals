package io.ayers.services.servicealbums.services.interfaces;

import io.ayers.services.servicealbums.models.domain.AlbumEntity;

import java.util.List;

public interface AlbumService {
    List<AlbumEntity> getAlbums(String userId);
}
