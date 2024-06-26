package br.com.alura.literAlura.dto;

import java.util.List;

public record AuthorDTO(String author,
                        int birthYear,
                        int deathYear,
                        List<String> books) {
}
