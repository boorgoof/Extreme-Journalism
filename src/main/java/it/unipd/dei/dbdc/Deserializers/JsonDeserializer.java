package it.unipd.dei.dbdc.Deserializers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipd.dei.dbdc.Interfaces.Deserializers.specificDeserializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDeserializer implements specificDeserializer<Article> {

    // TODO: mettere i campi giusti
    private String[] fields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "sectionId" };
    // preferisco usare un campo in più di supporto rispetto alle json properties. (in caso vediamo)

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] newFields) {
        if( newFields.length == fields.length){
            fields = newFields;
        }
        else throw new IllegalArgumentException("Deve essere fornito un array di dimensione " + fields.length);
    }

    @Override
    public List<Article> deserialize(String filePath) throws IOException {

        List<Article> articles = new ArrayList<>();

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

        return articles;
    }

}

// idee di miglioramento. deserializzare in modo dinamico
/*
  public void setFields(String[] fields) {
        Constructor<?>[] constructors = Article.class.getConstructors();

        for (Constructor<?> constructor : constructors) {
            int numConstructorParams = constructor.getParameterCount();

            if (fields.length == numConstructorParams) {
                this.fields = fields;
                return;
            }
        }

        throw new IllegalArgumentException("Nessun costruttore di Article corrisponde al numero di campi specificato.");
    }
*/