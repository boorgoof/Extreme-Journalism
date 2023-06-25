package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

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
        id = null;
        url = null;
        title = null;
        body = null;
        date = null;
        sourceSet = null;
        source = null;
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

    public Article(String[] values) throws IllegalArgumentException
    {
        if (values.length != 7) {
            throw new IllegalArgumentException("The array should contain 7 values");
        }

        id = values[0];
        url =  values[1];
        title =  values[2];
        body = values[3];
        date = values[4];
        sourceSet = values[5];
        source = values[6];
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

    @Override
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
        if (!(obj instanceof Article)) {
            return false;
        }
        Article article = (Article) obj;

        //We can't use the form id.equals() because the id may be null.
        return  Objects.equals(id, article.id) &&
                Objects.equals(url, article.url) &&
                Objects.equals(title, article.title) &&
                Objects.equals(body, article.body) &&
                Objects.equals(date, article.date) &&
                Objects.equals(sourceSet, article.sourceSet) &&
                Objects.equals(source, article.source);
    }

}