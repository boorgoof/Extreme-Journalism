package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnirestException, IOException {
        Map<String, Object> fields = new HashMap<>();
        fields.put("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f");
        fields.put("page-size", "4");

        // Si puo' usare queryString("apiKey", "afaqojfva") oppure fields(fields).
        // Questo ci permette di avere una richiesta di tipo GET
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("https://content.guardianapis.com/search").queryString("api-key", ".....................").asJson();
        System.out.println(jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody());

        Unirest.shutdown();
    }
}
