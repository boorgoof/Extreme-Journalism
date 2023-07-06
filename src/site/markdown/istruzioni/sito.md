# Generazione del sito
Il sito viene generato con il Maven Site Plugin: il codice sorgente per la generazione del sito è nel folder `src/site`.
Le istruzioni per creare e rendere disponibile il sito sono:

    mvn site
    mvn site:run

Il sito sarà quindi disponibile al seguente indirizzo: [http://localhost:8080/](http://localhost:8080/)
Esso contiene i javadocs e i reports dei test, oltre alla documentazione del progetto.
I suoi vari file saranno disponibili all'interno del folder `target/site`