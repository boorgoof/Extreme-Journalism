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
     */
    private String[] fields = {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" };

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
        if (newFields.length == maxLength){
            fields = newFields;
        } else {
            throw new IllegalArgumentException("You cannot insert an array of different length by the number of fields than those declared in the Article class");
        }

    }

    /**
     * The function deserializes a JSON file in {@link List} of {@link Serializable} objects which are instances of {@link Article}
     * To perform the deserialization, all the nodes present in the entire file that contain the key {@link JsonArticleDeserializer#fields}[0] are found in them.
     * By default {@link JsonArticleDeserializer#fields}[0] ="id" (it can be changed like all other search fields).
     * So in a file, in the default case, as many articles are identified as the "id" value keys are present inside it.
     * Once all the nodes have been found that have an article inside them, each of them is parsed.
     * So an article is formed by the values associated with these keys both in the JSON file and in {@link JsonArticleDeserializer#fields}.
     * <code>
     * For example:
     *"object": {
     *     "data": [
     *       {
     *         "response1": {
     *           "id" : "ID 1",
     *           "url" : "URL 1",
     *           "title" : "Title 1",
     *           "body" : "Body 1",
     *           "date" : "Date 1",
     *           "sourceSet" : "sourceSet 1",
     *           "source" : "Source 1"
     *         }
     *       },
     *       {
     *         "response2": {
     *           "id" : "ID 2",
     *           "url" : "URL 2",
     *           "title" : "Title 2",
     *           "body" : "Body 2",
     *           "date" : "Date 2",
     *           "sourceSet" : "sourceSet 2",
     *           "source" : "Source 2"
     *         },
     *         "response3": {
     *           "url" : "URL 3",
     *           "title" : "Title 3",
     *           "body" : "Body 3",
     *           "date" : "Date 3",
     *           "sourceSet" : "sourceSet 3",
     *           "source" : "Source 2"
     *         }
     *       }
     *     ]
     *   }
     * </code>
     * In this case, only the response1 and response2 nodes are parsed. So the deserialization of the file expects two {@link Article} objects.
     * The "id" key is not present in response3, so it is not considered as an {@link Article}
     * It is possible to change the keys that define an article with the method {@link JsonArticleDeserializer#setFields(String[])}
     *
     * @param jsonFile The JOSN  file to deserialize into {@link List} of {@link Serializable} objects which are instances of {@link Article}
     * @return the list of {@link Serializable} objects obtained from deserialization
     * @throws IOException If an I/O error occurs during the deserialization process. //TODO FIx commento errori
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

        // Create an ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        // Read the JSON file and obtain the root JsonNode
        JsonNode jsonNode = mapper.readTree(jsonFile);

        // Find all nodes that have the specified ID field (each node found contains an article)
        List<JsonNode> articleNodes = jsonNode.findParents(fields[0]);

        // Each of them is parsed in an article object
        for (JsonNode node : articleNodes) {

            Article article = parseNode(node);
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

        // Create an array to store the deserialized strings from the JSON file to associate with the Article object.
        // The length of the array is equal to the number of fields declared by the Article class
        Class<Article> myClass = Article.class;
        String[] fieldsValues = new String[myClass.getDeclaredFields().length];

        for(int i=0; i < fields.length; i++){
            // Handle cases where the key may not exist. So if the value associated with a key is not null, it is deserialized and stored in the array
            if(node.findValue(fields[i]) != null && !node.findValue(fields[i]).asText().equals("null")){
                fieldsValues[i] = node.findValue(fields[i]).asText();
            } else {
                fieldsValues[i] = null;
            }
        }
        // Create a new Article object using the field values obtained from deserialization contained in the array
        return new Article(fieldsValues);
    }

}

