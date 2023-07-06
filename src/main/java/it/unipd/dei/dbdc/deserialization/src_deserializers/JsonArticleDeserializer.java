package it.unipd.dei.dbdc.deserialization.src_deserializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the interface: {@link DeserializerWithFields}.
 * It is used to deserialize JSON files into a list of {@link Serializable} objects that are instances of {@link Article}.
 * This class uses the Jackson library.
 */
public class JsonArticleDeserializer implements DeserializerWithFields {

    /**
     * An array of {@link String} containing the fields that are taken into account when deserializing the JSON file
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

        int maxLength = Article.class.getDeclaredFields().length;
        if (newFields.length <= maxLength){
            fields = newFields;
        } else {
            throw new IllegalArgumentException("You cannot insert an array with more fields than those declared in the Article class");
        }

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
    public List<Serializable> deserialize(File jsonFile) throws IOException {

        if(jsonFile == null){
            throw new IllegalArgumentException("The JSON file cannot be null");
        }
        if (!jsonFile.exists()) {
            throw new IllegalArgumentException("The JSON file does not exist");
        }

        List<Serializable> articles = new ArrayList<>();

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
     * Performs the  deserialization of a JSON node into an object of type Article ("POJO")
     *
     * @param node The JSON node to parse.
     * @return An {@link Article} object obtained from the parsed node.
     */
    private Article parseNode(JsonNode node) {

        // Create an array to store the deserialized strings from the CSV file to associate with the Article object.
        // The length of the array is equal to the number of fields declared by the Article class
        Class<Article> myClass = Article.class;
        String[] fieldsValues = new String[myClass.getDeclaredFields().length];

        // serve per accettare i casi in cui non esiste la chiave nel file json altrimenti avrei nullPointerException con asText()
        for(int i=0; i < fields.length; i++){

            if(node.findValue(fields[i]) != null && !node.findValue(fields[i]).asText().equals("null")){
                fieldsValues[i] = node.findValue(fields[i]).asText();
            } else {
                fieldsValues[i] = null;
            }
        }
        // Create a new Article object using the field values obtained from deserialization
        return new Article(fieldsValues);
    }

}

