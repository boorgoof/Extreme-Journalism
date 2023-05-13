package it.unipd.dei.dbdc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// jsonProperties ora non servono piu con jsonNode. devo vedere quale è il modo migliore
@JsonIgnoreProperties(ignoreUnknown = true) // oppure potevo fare quando creo il mio oggetto:   objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // è essenziale per ignorare i campi aggiuntivi
public class Article {

    private String ID;
    private String URL;
    @JsonProperty("headLine")
    private String title;
    @JsonProperty("bodyText")  // @JsonAlias({"bodyText", "main"}) sono due possibili tag che ci vanno bene , se ci sono entrambi prende il primo ignora il secondo. devo importare import com.fasterxml.jackson.annotation.JsonAlias;
    private String body;
    private String Date;
    private String Source;

    public Article(String ID, String URL, String title, String body, String date, String source) {
        this.ID = ID;
        this.URL = URL;
        this.title = title;
        this.body = body;
        Date = date;
        Source = source;
    }

    public Article() {
    }

    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getID() {
        return ID;
    }

    public String getURL() {
        return URL;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return Date;
    }

    public String getSource() {
        return Source;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setSource(String source) {
        Source = source;
    }

    @Override
    public String toString() {
        return "Article{" +
                "ID='" + ID + '\'' +
                ", URL='" + URL + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", Date='" + Date + '\'' +
                ", Source='" + Source + '\'' +
                '}';
    }
}
