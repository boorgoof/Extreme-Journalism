package it.unipd.dei.dbdc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// jsonProperties ora non servono piu con jsonNode. devo vedere quale è il modo migliore
@JsonIgnoreProperties(ignoreUnknown = true) // oppure potevo fare quando creo il mio oggetto:   objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // è essenziale per ignorare i campi aggiuntivi
public class Article {

    @JsonProperty("headLine")
    private String title;
    @JsonProperty("bodyText")  // @JsonAlias({"bodyText", "main"}) sono due possibili tag che ci vanno bene , se ci sono entrambi prende il primo ignora il secondo. devo importare import com.fasterxml.jackson.annotation.JsonAlias;
    private String body;


    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
