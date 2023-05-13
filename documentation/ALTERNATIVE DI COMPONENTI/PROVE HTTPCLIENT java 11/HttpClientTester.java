package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.*;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class HttpClientTester
{
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String uritot = "https://content.guardianapis.com/search?api-key=............&page-size=200&q=nuclear&page=1&show-fields=bodyText,headline&order-by=newest&format=json";

        // HTTPRequest e' la richiesta che mandiamo
        // New instances can be created using HttpRequest.Builder. Al costruttore possiamo dare direttamente il nostro URI
        // Potrebbe lanciare URISyntaxException
        // Per decidere cosa metterci mettiamo .GET()
        HttpRequest request = HttpRequest.newBuilder().uri(new java.net.URI(uritot)).GET().build();

        // Possiamo mettere anche un timeout dopo cui lancia HttpTimeoutException: newBuilder().timeout(Duration.of(10, SECONDS))

        // HttpClient e' l'oggetto tramite cui mandiamo le richieste (tramite send(), che aspetta la risposta).
        // Viene istanziato con newBuilder() (in questo caso ricordati di mettere .build() alla fine) o con newHttpClient()
        // Il vantaggio del primo metodo e' che posso metterci dei parametri miei
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        // Per mandare la richiesta si usa la richiesta e si ottiene una risposta. In questo caso la salva su un file
        long start = System.currentTimeMillis();
        HttpResponse<java.nio.file.Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get("./database/example.json")));
        long end = System.currentTimeMillis();

        // La risposta si trova quindi in HttpResponse, che ha 2 metodi:
        // Ritorna un int
        System.out.println(response.statusCode());
        // Ritorna un formato che dipende dal BodyHandler, quindi una stringa oppure un file..
        // Possiamo poi ottenere URI di chi ha risposto, headers, protocollo http...

        System.out.println("Time of computing pageSize=200: " + (end-start) + " milliseconds");
    }
}