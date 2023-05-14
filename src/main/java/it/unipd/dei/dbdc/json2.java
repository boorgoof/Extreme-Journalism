package it.unipd.dei.dbdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// da completare con i campi aggiuntivi (ossia id, date, source). E' buono ma forse json1 lo reputo migliore.
public class json2 {
    public static void main(String[] args) {


        List<Article> articles = new ArrayList<>();

        String path_folder = ".\\database\\prova_guardian";
        String path_serialized_file = ".\\database\\fileSerializzato.json";

        deserialization_JSON_folder(path_folder,articles);
        serializazion_JSON_file(path_serialized_file, articles);

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
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);

            extractArticles(rootNode, articles);

            for (Article article : articles) {
                System.out.println(article.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractArticles(JsonNode node, List<Article> articles) {

        if (node.isObject()) {

            JsonNode headlineNode = node.get("headline");
            JsonNode bodytextNode = node.get("bodyText");

            if (headlineNode != null && bodytextNode != null) {
                articles.add(new Article(headlineNode.asText(), bodytextNode.asText()));
            }

            for (JsonNode child : node) {
                extractArticles(child, articles);
            }

        } else if (node.isArray()) {
            for (JsonNode child : node) {
                extractArticles(child, articles);
            }
        }
    }
    public static void serializazion_JSON_file(String path, List<Article> articles){

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
