package io.ayers.services.serviceaccounts.feign;

import io.ayers.services.serviceaccounts.models.response.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PHOTO-APP-SERVICE-ALBUMS", fallbackFactory = AlbumServiceFeignFallbackFactory.class)
public interface AlbumServiceFeign {

    @GetMapping(path = "/users/{userId}/albums")
    List<AlbumResponseModel> getAlbums(@PathVariable("userId") String userId);

}


