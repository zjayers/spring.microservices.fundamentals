package io.ayers.services.servicealbums.controllers;

import io.ayers.services.servicealbums.models.domain.AlbumEntity;
import io.ayers.services.servicealbums.models.response.AlbumResponseModel;
import io.ayers.services.servicealbums.services.interfaces.AlbumService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts/{id}/albums")
@RequiredArgsConstructor
public class AlbumsController {

    private final AlbumService albumService;

    @GetMapping(produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String id) {

        List<AlbumResponseModel> returnValue = new ArrayList<>();

        List<AlbumEntity> albumsEntities = albumService.getAlbums(id);

        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();
        returnValue = new ModelMapper().map(albumsEntities, listType);

        return returnValue;
    }
}
