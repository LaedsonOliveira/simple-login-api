package com.dev.laedson.simple_auth_api.client.dto;

import com.dev.laedson.simple_auth_api.entity.Stock;

import java.util.List;

public record BrapiResponseDto(List<StockDTO>  results) {
}
