package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.METODI_DESERIALIZZAZIONE_SERIALIZZAZIONE_PROVE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class json3 {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        List<Article> articles = new ArrayList<>();
        try {
            // Read JSON file into JsonNode
            JsonNode jsonNode = mapper.readTree(new File("D:\\ingengeria software\\eis-final\\database\\databaseProva\\Articles012.json"));

            // Iterate over JSON array elements
            if (jsonNode.isArray()) {
                for (JsonNode item : jsonNode) {


                    Article article = new Article(
                            item.get("id").asText(),
                            item.get("url").asText(),
                            item.get("title").asText(),
                            item.get("body").asText(),
                            item.get("date").asText(),
                            item.get("source").asText()
                    );
                    articles.add(article);
                }
            }

            for (Article article : articles){
                System.out.println(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}