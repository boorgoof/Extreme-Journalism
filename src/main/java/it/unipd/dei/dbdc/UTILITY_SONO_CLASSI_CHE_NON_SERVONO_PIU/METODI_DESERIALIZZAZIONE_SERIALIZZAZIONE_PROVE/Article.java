package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.METODI_DESERIALIZZAZIONE_SERIALIZZAZIONE_PROVE;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

// jsonProperties ora non servono piu con jsonNode ( perche uso jsonNode)
// se voglamo usarle dobbiamo vedere come fare in modo efficacie.

/*
    E' possibile utilizzare librerie di terze parti che consentono la serializzazione senza l'implementazione
    dell'interfaccia Serializable di Java. Queste librerie spesso offrono meccanismi di serializzazione
    alternativi che possono essere utilizzati per oggetti non serializzabili in modo nativo.

    Ad esempio, in Java, puoi utilizzare librerie come Jackson, Gson o XStream per serializzare oggetti in formato
    JSON o XML senza dover implementare l'interfaccia Serializable. Queste librerie utilizzano metodi di riflessione
    o altre strategie per ottenere i dati dell'oggetto e convertirli in una rappresentazione serializzabile.

    Tuttavia, è importante notare che l'utilizzo di queste librerie può richiedere configurazioni aggiuntive
     e potrebbe essere necessario gestire manualmente determinati aspetti della serializzazione, come la gestione
     di attributi transienti o riferimenti circolari. Inoltre, la portabilità dei dati serializzati tra diverse
     piattaforme o linguaggi potrebbe non essere garantita.

    L'interfaccia Serializable di Java, d'altra parte, è il meccanismo di serializzazione nativo offerto dal linguaggio e
    viene supportata in modo nativo dal motore di serializzazione di Java. Se possibile, è consigliabile utilizzare
    l'implementazione Serializable di Java per garantire la compatibilità e la facilità d'uso tra le diverse parti del
    tuo sistema Java. Tuttavia, se hai requisiti specifici o preferenze per l'uso di librerie di terze parti per la
    serializzazione, puoi esplorare e utilizzare le alternative disponibili.

    l'interfaccia serializzable è cosi:

    public interface Serializable {
        // Nessun metodo dichiarato esplicitamente
    }

    Non c'è nulla dentro, serve solo per distinguere gli oggetti serializzabili dai non serializzabili
    nota: se nella mia classe avessi un attributo che non può essere incluso nella serializzazione lo avrei dovuto chiamare: private transient String Data;
*/


public class Article implements Serializable {
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

