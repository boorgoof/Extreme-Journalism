package it.unipd.dei.dbdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Soluzione che preferisco.
// Trovo i nodi genitori e poi prendo i campi che mi interessano con i nodi genitori.


public class json1 {

    public static final String ID = "id" ;
    public static final String URL = "webUrl";
    public static final String TITLE = "headline";
    public static final String BODY = "bodyText";
    public static final String DATE = "firstPublicationDate";
    public static final String SOURCE = "publication";

    public static void main( String[] args )
    {
        List<Article> articles = new ArrayList<>();

        String path_folder = ".\\database\\prova_guardian";
        String path_serialized_json = ".\\database\\fileSerializzato.json";

        deserialization_JSON_folder(path_folder,articles);
        serialization_JSON_file(path_serialized_json, articles);

    }

    public static void deserialization_JSON_folder(String path, List<Article> articles){

        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    deserialization_JSON_file(file, articles);
                }
            }
        }

    }
    public static void deserialization_JSON_file(File jsonFile, List<Article> articles){

        try {

            // TODO: spostarlo fuori da qui (vedi App.java)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonFile);

            // Trova tutti i nodi che hanno ID dentro (come figlio, quindi sono gli articoli)
            List<JsonNode> ArticleParentNodes = jsonNode.findParents(ID);

            for (JsonNode ParentNode : ArticleParentNodes) {

                Article article = new Article(ParentNode.findValue(ID).asText(), ParentNode.findValue(URL).asText(), ParentNode.findValue(TITLE).asText(),ParentNode.findValue(BODY).asText(),ParentNode.findValue(DATE).asText(),ParentNode.findValue(SOURCE).asText());

                articles.add(article);
                System.out.print(article.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void serialization_JSON_file(String path, List<Article> articles){

        try{

            // Creazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

            // Serializzazione della lista di articoli in un file JSON
            objectMapper.writeValue(new File(path), articles);

            // Serializzazione della lista di articoli in una stringa JSON
            String stringaJson = objectMapper.writeValueAsString(articles);
            System.out.println(stringaJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


// Modo di scrivere forse un pochino più sintetico, non saprei cosa è meglio.
// Sapete se esiste un modo per fare un costruttore con tutti i campi di un array?
/*
    public static final String[] fields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };

    public static void deserialization_JSON_file(File jsonFile, List<Article> articles) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonFile);

            // Trovo ogni nodo che ha id come campo
            List<JsonNode> ArticleParentNodes = jsonNode.findParents(fields[0]);

            for (JsonNode ParentNode : ArticleParentNodes) {

                String[] fieldsFound = new String[fields.length];

                // Metto dentro a un vettore tutti quelli trovati.
                for(int i = 0; i < fields.length; i++){
                    fieldsFound[i] = ParentNode.findValue(fields[i]).asText();
                }

                // Per voi è meglio con le costanti o cosi con il vettore?

                Article article = new Article(fieldsFound[0], fieldsFound[1], fieldsFound[2], fieldsFound[3], fieldsFound[4], fieldsFound[5]);
                articles.add(article);
                System.out.print(article.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 */