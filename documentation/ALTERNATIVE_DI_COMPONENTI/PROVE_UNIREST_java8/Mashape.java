//package org.example;

// Molto deprecato, non ha molto senso

/*
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.ObjectMapper;


import java.io.IOException;
import java.util.*;

public class Mashape
{
    public static void main( String[] args ) throws UnirestException, IOException {
        // 1. Setto l'objectmapper che serve per serializzare e deserializzare
        ObjectMapper mapper = new MyObjectMapper();
        // Attenzione: il mapper che metto dentro non puo' essere di jackson, ma deve essere del tipo mashape.ObjectMapper

        Map<String, Object> fields = new HashMap<>();
        fields.put("api-key", "....................");
        fields.put("page-size", "4");

        // Si puo' usare queryString("apiKey", "afaqojfva") oppure fields(fields).
        // Questo ci permette di avere una richiesta di tipo GET
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("https://content.guardianapis.com/search").queryString("api-key", ".................").asJson();
        System.out.println(jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody());

        Unirest.shutdown();
    }
}

// Questa e' la classe utilizzabile per com.mashape.unirest, che sfrutta l'ObjectMapper di Jackson.
// Il funzionamento e' di fatto lo stesso di kong.Unirest, e un esempio e' in clien-theGuardianApi
class MyObjectMapper implements ObjectMapper
{
    // Questo e' l'objectmapper di jackson
    private final com.fasterxml.jackson.databind.ObjectMapper map = new com.fasterxml.jackson.databind.ObjectMapper();

    // Trasforma un JSON in un oggetto della classe valueType
    public <T> T readValue(String value, Class<T> valueType)
    {
        try {
            return map.readValue(value, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Trasforma un oggetto in JSON, che viene ritornato come String
    public String writeValue(Object value)
    {
        try {
            return map.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
*/