package it.unipd.dei.dbdc;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        // esempio serializzazione e deserializzazione con la libreria gson.
        // non so se esiste per ogni formato per farla generale,
        // dunque non credo che sia il metodo migliore era tanto per provare a serializzare e deserializzare

        final String jsonProva = "{\n" +
                "    \"title\": \"nuclear effect\",\n" +
                "    \"body\": \"ciccio gamer\"\n" +
                "}";
        final Gson gson= new Gson();

        // deserializzazione

        Article a1 = gson.fromJson(jsonProva, Article.class);

        System.out.println("title: "+ a1.getTitle());
        System.out.println("body: "+ a1.getBody());

        // serializzazione

        Article a2 = new Article("case in montagna", "sono belle ma costose");

        String jsonSerializzato = gson.toJson(a2);

        System.out.println(jsonSerializzato);


    }

}
