package it.unipd.dei.dbdc.DESERIALIZERS;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonDeserializer implements Deserializer<Article>{

    private String[] fields;

    public JsonDeserializer(String[] fields) {
        this.fields = fields;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    @Override
    public List<Article> deserialize(String filePath) throws IOException {

        List<Article> articles = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(new File(filePath));

            // Trova tutti i nodi che hanno ID dentro (come figlio, quindi sono gli articoli)
            List<JsonNode> articleParentNodes = jsonNode.findParents(fields[0]);

            for (JsonNode parentNode : articleParentNodes) {
                Article article = new Article(
                        parentNode.findValue(fields[0]).asText(),
                        parentNode.findValue(fields[1]).asText(),
                        parentNode.findValue(fields[2]).asText(),
                        parentNode.findValue(fields[3]).asText(),
                        parentNode.findValue(fields[4]).asText(),
                        parentNode.findValue(fields[5]).asText()
                );

                System.out.println(article);
                articles.add(article);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

}
