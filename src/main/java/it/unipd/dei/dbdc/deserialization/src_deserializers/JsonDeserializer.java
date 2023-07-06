package it.unipd.dei.dbdc.deserialization.src_deserializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the interface: {@link DeserializerWithFields}.
 * It is used to deserialize JSON files into a list of {@link UnitOfSearch} objects.
 * This class uses the Jackson library.
 */
public class JsonDeserializer implements DeserializerWithFields {

    /**
     * An array of {@link String} containing the fields that are taken into account when deserializing the csv file
     */ //TODO mettere i campi corretti
    private String[] fields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "webUrl", "webUrl" };

    /**
     * Provides the fields taken into account during deserialization
     *
     * @return An array of {@link String} representing the fields.
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * Provides the number of fields taken into account during deserialization
     *
     * @return An {@link Integer} representing number of fileds
     */
    public int numberOfFields(){
        return fields.length;
    }

    /**
     * sets the new fields to be considered during deserialization
     */
    public void setFields(String[] newFields) {
        fields = newFields;
    }

    /**
     * The function deserializes a JSON file in {@link List} of {@link UnitOfSearch}
     * // todo da fare commento
     *
     * @param jsonFile The JOSN  file to deserialize into {@link UnitOfSearch}
     * @return the list of {@link UnitOfSearch} objects obtained from deserialization
     * @throws IOException If an I/O error occurs during the deserialization process. //TODO FIx commento
     * @throws IllegalArgumentException if the file does not exist or is not null
     */
    @Override
    public List<UnitOfSearch> deserialize(File jsonFile) throws IOException {

        if(jsonFile == null){
            throw new IllegalArgumentException("The JSON file cannot be null");
        }
        if (!jsonFile.exists()) {
            throw new IllegalArgumentException("The JSON file does not exist");
        }

        List<UnitOfSearch> articles = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonFile);

        // Trova tutti i nodi che hanno ID dentro (come figlio, quindi sono gli articoli)
        List<JsonNode> articleParentNodes = jsonNode.findParents(fields[0]);

        for (JsonNode parentNode : articleParentNodes) {

            Article article = parseNode(parentNode);
            //System.out.println(article);
            articles.add(article);

        }

        return articles;
    }

    /**
     * Parses a JSON node into an {@link Article} object.
     *
     * @param node The JSON node to parse.
     * @return An {@link Article} object obtained from the parsed node.
     */
    private Article parseNode(JsonNode node) {

        Class<Article> myClass = Article.class;
        String[] fieldsValues = new String[myClass.getDeclaredFields().length];

        // serve per accettare i casi in cui non esiste la chiave nel file json altrimenti avrei nullPointerException con asText()
        for(int i=0; i < fields.length; i++){
            // vedere se è meglio findValue() oppure get()
            if(node.findValue(fields[i]) != null && !node.findValue(fields[i]).asText().equals("null")){
                fieldsValues[i] = node.findValue(fields[i]).asText();
            } else {
                fieldsValues[i] = null;
            }
        }

        return new Article(fieldsValues);
    }

}

