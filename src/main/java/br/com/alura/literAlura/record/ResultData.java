package br.com.alura.literAlura.record;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultData(@JsonAlias("results") List<BookData> books) {
}
