package br.com.alura.literAlura.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(int count,
                          List<Object> results) {
}
