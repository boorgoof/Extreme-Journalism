package com.apiguardian;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.apiguardian.bean.Response;
import com.apiguardian.bean.ResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class GuardianContentApi {

  static
  {
    // Only one time. E' l'object mapper che permette di trasformare la risposta in Json in un oggetto direttamente
    // TODO CAPIRE QUESTO
    Unirest.setObjectMapper(new ObjectMapper()
    {
      // Questo oggetto, che è fasterxml.jackson.databind.ObjectMapper(), ha le funzioni al suo interno per serializzare e deserializzare
      private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
              = new com.fasterxml.jackson.databind.ObjectMapper();

      //Funzione readValue dell'object mapper. Serve per deserializzare un JSON in un oggetto
      public <T> T readValue(String value, Class<T> valueType)
      {
        try
        {
          return jacksonObjectMapper.readValue(value, valueType);
        }
        catch (IOException e)
        {
          throw new RuntimeException(e);
        }
      }

      //Funzione writeValue dell'object mapper. Serve per serializzare l'oggetto in un JSON
      public String writeValue(Object value)
      {
        try
        {
          return jacksonObjectMapper.writeValueAsString(value);
        }
        catch (JsonProcessingException e)
        {
          throw new RuntimeException(e);
        }
      }
    });
  }

  // Fields
  private final static String TARGET_URL = "http://content.guardianapis.com/search";
  private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private final String apiKey;
  private String section;
  private String tag;
  private Date toDate;
  private Date fromDate;

  // COSTRUTTORE: ci mette dentro la apikey
  public GuardianContentApi(final String apiKey) {
    this.apiKey = apiKey;
  }

  // Setters delle varie cose
  public void setSection(String section) {
    this.section = section;
  }

  public void setFromDate(Date date) {
    this.fromDate = date;
  }

  public void setToDate(Date date) {
    this.toDate = date;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  //Getter della query (in questo caso nulla) passata, che e' un oggetto Response
  public Response getContent() throws UnirestException
  {
    return getContent(null);
  }

  //Getter della query passata (la cosa da metter in q=), che e' un oggetto Response
  public Response getContent(String query) throws UnirestException
  {
    HttpRequest request = Unirest.get(TARGET_URL)
        .queryString("api-key", apiKey)
        .header("accept", "application/json");
    if (query != null && !query.isEmpty()) {
      request.queryString("q", query);
    }

    // Qua controlla se per caso prima ho settato i campi dell'API
    if (section != null && !section.isEmpty()) {
      request.queryString("section", section);
    }

    if (tag != null && !tag.isEmpty()) {
      request.queryString("tag", tag);
    }

    if (fromDate != null){
      request.queryString("from-date", dateFormat.format(fromDate));
    }
    if (toDate != null){
      request.queryString("to-date", dateFormat.format(toDate));
    }

    //Prende la risposta come un oggetto di tipo ResponseWrapper. Per farlo devo passare la classe dell'oggetto come parametro di asObject
    HttpResponse<ResponseWrapper> response = request.asObject(ResponseWrapper.class);

    // Il getBody mi restituisce l'oggetto in questione
    return response.getBody().getResponse();

  }
}
