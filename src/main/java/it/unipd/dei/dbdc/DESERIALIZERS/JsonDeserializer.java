package it.unipd.dei.dbdc.DESERIALIZERS;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// lo chiamereste JsonArticleDeserializer?
public class JsonDeserializer implements Deserializer{

    public static final String ID = "id" ;

    public static final String URL = "webUrl";
    public static final String TITLE = "headline";
    public static final String BODY = "bodyText";
    public static final String DATE = "firstPublicationDate";
    public static final String SOURCE = "publication";

    @Override
    public List<Object> deserialize(String[] fields, String filePath) throws IOException {
        List<Object> objects = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(new File(filePath));

            // Trova tutti i nodi che hanno ID dentro (come figlio, quindi sono gli articoli)
            List<JsonNode> articleParentNodes = jsonNode.findParents(ID);

            for (JsonNode parentNode : articleParentNodes) {
                Article article = new Article(
                        parentNode.findValue(ID).asText(),
                        parentNode.findValue(URL).asText(),
                        parentNode.findValue(TITLE).asText(),
                        parentNode.findValue(BODY).asText(),
                        parentNode.findValue(DATE).asText(),
                        parentNode.findValue(SOURCE).asText()
                );

                objects.add(article);
                System.out.print(article.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }
}
