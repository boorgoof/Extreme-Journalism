package it.unipd.dei.dbdc.Deserializers;


import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {

    private String id;

    private String url;

    private String title;

    private String body;

    private String date;

    private String source;

    public Article(String ID, String URL, String headline, String bodyText, String date, String source) {
        id = ID;
        url = URL;
        title = headline;
        body = bodyText;
        this.date = date;
        this.source = source;
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
        return date;
    }

    public String getSource() {
        return source;
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
        date = date;
    }

    public void setSource(String source) {
        source = source;
    }

    @Override
    public String toString() {
        return "Article{" +
                "ID='" + id + '\'' +
                ", URL='" + url + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", Date='" + date + '\'' +
                ", Source='" + source + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Article article = (Article) obj;

        return Objects.equals(id, article.id) &&
                Objects.equals(url, article.url) &&
                Objects.equals(title, article.title) &&
                Objects.equals(body, article.body) &&
                Objects.equals(date, article.date) &&
                Objects.equals(source, article.source);
    }


}
/*
return id.equals(article.id) &&
                url.equals(article.url) &&
                title.equals(article.title) &&
                body.equals(article.body) &&
                date.equals(article.date) &&
                source.equals(article.source);
 */

/*
 @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;

        Article article = (Article) obj;

        return  Objects.equals(id, article.id) &&
                Objects.equals(url, article.url) &&
                Objects.equals(title, article.title) &&
                Objects.equals(body, article.body) &&
                Objects.equals(date, article.date) &&
                Objects.equals(source, article.source);
    }
 */