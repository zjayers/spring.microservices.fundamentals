package io.ayers.services.serviceaccounts.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class FeignErrorDecoder
        implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                log.error("Status code " + response.status() + ", methodKey = " + methodKey);
            case 404: {
                log.error("Error took place when using Feign client to send HTTP Request. Status code " + response
                        .status() + ", methodKey = " + methodKey);
                return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                        "<error message description here>");
            }
            default:
                return new Exception(response.reason());
        }

    }
}
