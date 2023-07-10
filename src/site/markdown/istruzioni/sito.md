# Generazione del sito
Il sito viene generato con il Maven Site Plugin: il codice sorgente per la generazione del sito è nel folder `src/site`.
Prima di creare il sito è consigliato [installare il progetto](installazione.html). Infatti, la 
generazione del sito non comporta l'esecuzione dei vari test (volutamente, per non eseguirli due volte).
Le istruzioni per creare e rendere disponibile il sito sono:

    mvn site
    mvn site:run

Il sito sarà quindi disponibile al seguente indirizzo: [http://localhost:8080/](http://localhost:8080/)
Esso contiene anche i reports dei test, oltre alla documentazione del progetto.
I suoi vari file saranno disponibili all'interno del folder `target/site`

Per terminare l'esecuzione di site:run (una volta chiusa la pagina web) digitare due volte da riga di comando CTRL+C (da dove si era avviato il sito).