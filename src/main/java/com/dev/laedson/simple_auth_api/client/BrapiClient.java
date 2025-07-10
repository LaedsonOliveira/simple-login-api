package com.dev.laedson.simple_auth_api.client;

import com.dev.laedson.simple_auth_api.client.dto.BrapiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "BrapiCliente",
        url = "https://brapi.dev"
)
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{quoteId}")
    BrapiResponseDto getQuote(@RequestParam("token") String token,
                              @PathVariable("stockId") String stockId);


}
