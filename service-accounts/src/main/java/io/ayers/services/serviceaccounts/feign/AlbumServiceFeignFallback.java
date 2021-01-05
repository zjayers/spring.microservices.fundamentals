package io.ayers.services.serviceaccounts.feign;

import feign.FeignException;
import io.ayers.services.serviceaccounts.models.response.AlbumResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AlbumServiceFeignFallback
        implements AlbumServiceFeign {

    private final Throwable cause;

    @Override
    public List<AlbumResponseModel> getAlbums(String userId) {
        log.error(
                cause instanceof FeignException && ((FeignException) cause).status() == 404
                ? String.format("404 Error: GetAlbums was called with userId: %s. Error message: %s",
                        userId,
                        cause.getLocalizedMessage())
                : "Error took place: " + cause.getLocalizedMessage());

        return new ArrayList<>();
    }
}
