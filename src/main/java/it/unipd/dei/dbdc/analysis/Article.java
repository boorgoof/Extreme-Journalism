package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.util.Objects;

/**
 * This class represents an article downloaded from any source.
 * It has an ID, a URL, a title, a body, a date, a source set and a source.
 * It implements {@link UnitOfSearch}, and permits to analyze only its body and title.
 *
 */
public class Article implements UnitOfSearch {
    /**
     * ID of the article
     *
     */
    private String id;
    /**
     * URL of the article
     *
     */
    private String url;
    /**
     * Title of the article
     *
     */
    private String title;
    /**
     * Body of the article
     *
     */
    private String body;
    /**
     * Date of the article
     *
     */
    private String date;
    /**
     * Source set of the article
     *
     */
    private String sourceSet;
    /**
     * Source of the article
     *
     */
    private String source;

    /**
     * Default constructor: everything is set as null.
     *
     */
    public Article() {
        id = null;
        url = null;
        title = null;
        body = null;
        date = null;
        sourceSet = null;
        source = null;
    }

    /**
     * Constructor of the article which requires every parameter.
     *
     * @param ID The ID of the article
     * @param URL The URL of the article
     * @param headline The title of the article
     * @param bodyText The body of the article
     * @param date The date of the article
     * @param sourceSet The source set of the article
     * @param source The source of the article
     */
    public Article(String ID, String URL, String headline, String bodyText, String date, String sourceSet, String source) {
        id = ID;
        url = URL;
        title = headline;
        body = bodyText;
        this.date = date;
        this.sourceSet = sourceSet;
        this.source = source;
    }

    /**
     * Constructor of the article which requires every parameter passed as an array of {@link String}.
     *
     * @param values The parameters of the article, in order: ID, URL, title, body, date, source set, source.
     * @throws IllegalArgumentException If the length of the array passed as a parameter is not 7.
     */
    public Article(String[] values) throws IllegalArgumentException
    {
        if (values.length != 7) {
            throw new IllegalArgumentException("The array to initialize the Article should contain 7 values");
        }

        id = values[0];
        url =  values[1];
        title =  values[2];
        body = values[3];
        date = values[4];
        sourceSet = values[5];
        source = values[6];
    }

    /**
     * Getter of the ID
     *
     * @return A {@link String} representing the article's ID.
     */
    public String getID() {
        return id;
    }

    /**
     * Getter of the URL
     *
     * @return A {@link String} representing the article's URL.
     */
    public String getURL() {
        return url;
    }

    /**
     * Getter of the title.
     *
     * @return A {@link String} representing the article's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter of the body.
     *
     * @return A {@link String} representing the article's body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter of the date.
     *
     * @return A {@link String} representing the article's date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter of the source set
     *
     * @return A {@link String} representing the article's source set.
     */
    public String getSourceSet() {
        return sourceSet;
    }

    /**
     * Getter of the source
     *
     * @return A {@link String} representing the article's source.
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter of the ID
     *
     * @param ID A {@link String} representing the article's id.
     */
    public void setID(String ID) {
        id = ID;
    }

    /**
     * Setter of the URL
     *
     * @param URL A {@link String} representing the article's URL.
     */
    public void setURL(String URL) {
        url = URL;
    }

    /**
     * Setter of the title.
     *
     * @param headline A {@link String} representing the article's title.
     */
    public void setTitle(String headline) {
        title = headline;
    }

    /**
     * Setter of the body.
     *
     * @param bodyText A {@link String} representing the article's body.
     */
    public void setBody(String bodyText) {
        body = bodyText;
    }

    /**
     * Setter of the date
     *
     * @param date A {@link String} representing the article's date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter of the source set.
     *
     * @param sourceSet A {@link String} representing the article's source set.
     */
    public void setSourceSet(String sourceSet) {this.sourceSet = sourceSet;}

    /**
     * Setter of the source
     *
     * @param source A {@link String} representing the article's source.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * The function that returns the text to analyze. Overrides the one of {@link UnitOfSearch}.
     *
     * @return A {@link String} representing the article's title and body, separated by a space.
     */
    @Override
    public String obtainText()
    {
        return getTitle()+" "+getBody();
    }

    /**
     * The function that returns the article as a String.
     *
     * @return A {@link String} representing the article's fields, all introduced by the name of the field and separated by commas.
     */
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

    /**
     * The function that compares two Articles.
     *
     * @return true only if the two articles have identical fields (even if they are null).
     */
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