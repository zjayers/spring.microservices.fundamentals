package io.ayers.services.serviceaccounts.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumServiceFeignFallbackFactory
        implements FallbackFactory<AlbumServiceFeign> {

    @Override
    public AlbumServiceFeign create(Throwable cause) {
        return new AlbumServiceFeignFallback(cause);
    }
}
