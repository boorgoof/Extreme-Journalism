package it.unipd.dei.dbdc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;


// jsonProperties ora non servono piu con jsonNode ( perche uso jsonNode)
// se voglamo usarle dobbiamo vedere come fare in modo efficacie.
public class Article {
    @CsvBindByName(column = "Identifier")
    //@CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByName(column = "URL")
    //@CsvBindByPosition(position = 1)
    private String url;
    @CsvBindByName(column = "Title")
    //@CsvBindByPosition(position = 2)
    private String title;
    @CsvBindByName(column = "Body")
    //@CsvBindByPosition(position = 3)
    private String body;
    @CsvBindByName(column = "Date")
    //@CsvBindByPosition(position = 4)
    private String Date;
    @CsvBindByName(column = "Source Set")
    //@CsvBindByPosition(position = 5)
    private String Source;

    public Article(String ID, String URL, String headline, String bodyText, String date, String source) {
        id = ID;
        url = URL;
        title = headline;
        body = bodyText;
        Date = date;
        Source = source;
    }

    public Article() {
    }

    public Article(String headline, String bodyText) {
        title = headline;
        body = bodyText;
    }

    public String getID() {
        return id;
    }

    public String getURL() {
        return url;
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
        id = ID;
    }

    public void setURL(String URL) {
        url = URL;
    }

    public void setTitle(String headline) {
        title = headline;
    }

    public void setBody(String bodyText) {
        body = bodyText;
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
                "ID='" + id + '\'' +
                ", URL='" + url + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", Date='" + Date + '\'' +
                ", Source='" + Source + '\'' +
                '}';
    }
}

