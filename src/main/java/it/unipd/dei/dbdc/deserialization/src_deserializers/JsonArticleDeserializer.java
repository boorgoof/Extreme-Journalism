package it.unipd.dei.dbdc.deserialization.src_deserializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dbdc.analysis.Article;
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
     * Default constructor of the class. It is the only constructor and it doesn't do anything,
     * as the fields' values are defined by default.
     *
     */
    public JsonArticleDeserializer() {}

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
     * Sets the new fields to be considered during deserialization
     *
     * @param newFields The new fields that will be considered during deserialization.
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
     * So an article consists of the values associated with the keys that are present in both the JSON file and {@link JsonArticleDeserializer#fields}.
     * <pre>
     *For example:
     *{
     *   "oggetto": {
     *     "dati": [
     *       {
     *         "risposta1": {
     *           "id": "ID 1",
     *           "apiUrl": "URL 1",
     *           "headline": "Titolo 1",
     *           "bodyText": "Corpo 1",
     *           "webPublicationDate": "Data 1",
     *           "publication": "sourceSet 1",
     *           "sectionName": ""source 1""
     *         }
     *       },
     *       {
     *         "risposta2": {
     *           "id": "ID 2",
     *           "apiUrl": "URL 2",
     *           "headline": "Titolo 2",
     *           "bodyText": "Corpo 2",
     *           "webPublicationDate": "Data 2",
     *           "publication": "sourceSet 2",
     *           "sectionName": "source 2"
     *         },
     *         "risposta3": {
     *           "apiUrl": "URL 3",
     *           "headline": "Titolo 3",
     *           "bodyText": "Corpo 3",
     *           "webPublicationDate": "Data 3",
     *           "publication": "sourceSet 3",
     *           "sectionName": "source 3"
     *         }
     *       }
     *     ]
     *   }
     * }
     * </pre>
     * In this case, only the response1 and response2 nodes are parsed. So the deserialization of the file expects two {@link Article} objects.
     * The "id" key is not present in response3, so it is not considered as an {@link Article}
     * It is possible to change the keys that define an article with the method {@link JsonArticleDeserializer#setFields(String[])}
     *
     * @param jsonFile The JOSN  file to deserialize into {@link List} of {@link Serializable} objects which are instances of {@link Article}
     * @return the list of {@link Serializable} objects obtained from deserialization
     * @throws IOException If an I/O error occurs during the deserialization process.
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

