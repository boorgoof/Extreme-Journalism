package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.UnitOfSearch;

import java.util.Objects;

public class Article implements UnitOfSearch {
    private String id;
    private String url;
    private String title;
    private String body;
    private String date;
    private String sourceSet;
    private String source;

    public Article() {
    }

    public Article(String ID, String URL, String headline, String bodyText, String date, String sourceSet, String source) {
        id = ID;
        url = URL;
        title = headline;
        body = bodyText;
        this.date = date;
        this.sourceSet = sourceSet;
        this.source = source;
    }

    public static Article instanceArticle(String[] values) {

        if (values.length != 7) {
            throw new IllegalArgumentException("L'array deve contenere 7 valori.");
        }
        return new Article(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
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

    public String getSourceSet() {
        return sourceSet;
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
        this.date = date;
    }

    public void setSourceSet(String sourceSet) {this.sourceSet = sourceSet;};
    public void setSource(String source) {
        this.source = source;
    }

    public String obtainText()
    {
        return getTitle()+" "+getBody();
    }

    @Override
    public String toString() {
        return "Article{" +
                "ID='" + id + '\'' +
                ", URL='" + url + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", Date='" + date + '\'' +
                ", SourceSet='" + sourceSet + '\'' +
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
                Objects.equals(sourceSet, article.sourceSet) &&
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