package br.com.alura.literAlura.service;

import br.com.alura.literAlura.constant.literAlura;
import br.com.alura.literAlura.exception.BookNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;

public class ApiConsume {

    public String getData(String requestParam) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(MessageFormat.format(literAlura.GUTENDEX_API, requestParam));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String responseBody = response.body();

        if (isNoResultsFound(responseBody)) {
            throw new BookNotFoundException("Book not found on the web.");
        }
        return responseBody;
    }

    private boolean isNoResultsFound(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);
            return apiResponse.count() == 0 || apiResponse.results().isEmpty();
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

}
