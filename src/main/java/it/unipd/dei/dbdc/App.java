package it.unipd.dei.dbdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class App 
{
    public static void main( String[] args )
    {
        List<Article> articles = new ArrayList<>();
        String path_folder = "D:\\ingengeria software\\eis-final\\database\\the_guardian";
        deserializazion_JSON_folder(path_folder,articles);
        serializazion_JSON_file(path_folder, articles);
    }
    public static void deserializazion_JSON_file(File file, List<Article> articles){

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(file);

            JsonNode responseNode = rootNode.path("response");

            /* prova
            if (!responseNode.isMissingNode()) {        // if "response" node is exist
                System.out.println("status : " + responseNode.path("status").asText());
            }
            */

            JsonNode ResponseArray = responseNode.path("results");

            for (JsonNode root : ResponseArray) {

                JsonNode fieldsNode = root.path("fields");
                if (!fieldsNode.isMissingNode()) {        // if "response" node is exist
                    Article article = new Article(fieldsNode.path("headline").asText(), fieldsNode.path("bodyText").asText());
                    articles.add(article);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void serializazion_JSON_file(String path, List<Article> articles){

        // Serializzazione a partire dalla lista di Article
        try{

            // Creazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

            // Serializzazione della lista di articoli in un file JSON
            objectMapper.writeValue(new File("D:\\ingengeria software\\eis-final\\database\\fileSerializzato.json"), articles);

            // Serializzazione della lista di articoli in una stringa JSON
            String stringaJson = objectMapper.writeValueAsString(articles);
            System.out.println(stringaJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void deserializazion_JSON_folder(String path, List<Article> articles){

        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    deserializazion_JSON_file(file, articles);
                }
            }
        }

    }

}









