package it.unipd.dei.dbdc.ALTERNATIVE_DI_COMPONENTI.PROVE_UNIREST_java8;

import kong.unirest.*;

import java.io.File;
import java.util.Arrays;
public class Kong {
    public static void main( String[] args )
    {
        // A. OPZIONI DI CONFIGURAZIONE
        // 1. Il nostro buon unirest utilizza dei cookie che danno problemi, questo sembra risolvere.
        // Alternativa: .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
        Unirest.config().enableCookieManagement(false);

        // 2. Settare URL di default, per non doverlo specificare poi
        Unirest.config().defaultBaseUrl("https://content.guardianapis.com");
        //Unirest.get("/search").asString();

        // 3. followRedirects(true) che permette di essere reindirizzati. E' settato di default.


        // B. FUNZIONAMENTO DI BASE DEL GET
        // Questo ci permette di avere una richiesta di tipo GET
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("/search").queryString("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f").asJson();

        // Alcune cose che si possono avere con il get:
        // 1. Mettere un placeholder nell'URL e poi rimpiazzarlo con quello che vogliamo:
        Unirest.get("/{fruit}?api-key=21b5c154-934c-4a4e-b2f5-64adbd68af5f").routeParam("fruit", "search").asString();

        // 2. Mettere dentro i parametri con queryString (è come mettere ?fruit=apple&..)
        Unirest.get("/search")
                .queryString("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f")
                .queryString("page-size", "4")
                .asString();

        // Posso passarli anche come array
        Unirest.get("/search")
                .queryString("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f")
                .queryString("show-fields", Arrays.asList("headline", "body"))
                .asString();

        // 3. Aggiungere header
        //Unirest.get("http://localhost")
        //      .header("Accept", "application/json")
        //      .header("x-custom-header", "hello")
        //      .asString();

        // C. RISPOSTE
        // Le risposte possono essere sotto forma di String, File, Object, Json
        // OBJECT: per mapparli in un object abbiamo bisogno di un ObjectMapper
        // Un ObjectMapper gia' presente in kong e' il JsonObjectMapper(), basato su GSON
        ObjectMapper ob = new JsonObjectMapper();

        // Proviamo a leggere un JSON
        String prova = "{ \"status\" : \"ok\", \"pageSize\" : \"200\", \"currentPage\" : \"2\", \"nonSonoIo\" : \"ok\" }";
        MyResponse r = ob.readValue(prova, MyResponse.class);
        System.out.println(r);

        // Per fare asObject() dobbiamo prima definire un'implementazione dell'ObjectMapper.
        // Deve essere fatto una sola volta, e solo se non vogliamo usare quello di default
        // Response to Object
        HttpResponse<MyResponse> response = Unirest.get("/search").queryString("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f")
                .asObject(MyResponse.class);

        // In caso ci fossero problemi con il parsing, l'oggetto HTTPResponse conterrà un'eccezione da cui possiamo estrarre il body originale
        //UnirestParsingException ex = response.getParsingError().get();
        //ex.getOriginalBody(); // Has the original body as a string.

        // FILE:
        File result = Unirest.get("/search").queryString("api-key", "21b5c154-934c-4a4e-b2f5-64adbd68af5f")
                .asFile("./database/file_kong.json")
                .getBody();

        // D. HANDLING WITH ERRORS: l'oggetto HttpResponse ha un po di metodi:
        // ifSuccess(Consumer<HttpResponse<T>> response) will be called if the response was a 200-series response and any body processing (like json or Object) was successful.
        // ifFailure(Consumer<HttpResponse> response will be called if the status was 400+ or body processing failed.
        /* Unirest.get("http://somewhere")
                .asJson()
                .ifSuccess(response1 -> System.out.println("Success"+response1.getStatus()))
                .ifFailure(response1 -> {
                    System.out.println("Oh No! Status" + response1.getStatus());
                    response1.getParsingError().ifPresent(e -> {
                        System.out.println("Parsing Exception: ");
                        System.out.println("Original body: " + e.getOriginalBody());
                    });
                });
         */

        // LA RISPOSTA HA 2 METODI IMPORTANTI
        System.out.println(jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody());

        // Si possono fare anche richieste asincrone. TODO capire il perché

        // Per terminare
        Unirest.shutDown();
    }
}


// Classe per testare l'ObjectMapper di Kong
class MyResponse
{
    private String status;
    private int pageSize;
    private int currentPage;

    private int gianni;

    public String toString()
    {
        return "status: "+status+"\npageSize: "+pageSize+"\ncurrentPage: "+currentPage+"\ngianni: "+gianni;
    }
}

