# Installazione del progetto
Per installare il progetto basta posizionare la propria chiave per la theGuardian API nei seguenti file:
- `src/test/resources/trueApi.properties` (in api-key)
- `src/test/resources/trueApiTest.properties` (in api-key)
- `src/test/java/download/src_api_managers/TheGuardianAPI/GuardianAPIManager` (come una stringa, in key)

A quel punto si può digitare da riga di comando:

    mvn install

La parte di test richiede un po' di tempo, soprattutto per la classe `src/test/java/tools/ThreadPoolTest`.
L'installazione produce dei warning, che possono essere ignorati: sono dovuti al fatto che esistono file con lo stesso nome
nei vari artifacts che mettiamo nel jar con le dipendenze.

Al termine dell'installazione, si troverà una directory target in cui saranno presenti i file prodotti dall'installazione dell'applicazione.
Saranno presenti due jar:
* `Extreme_journalism-1.0-jar-with-dependencies.jar` è il jar che contiene tutte le dipendenze, e dunque è eseguibile;
* `Extreme_journalism-1.0.jar` è il jar che non contiene le dipendenze;

## Generazione dei javadocs
Per generare i javadocs, digitare da riga di comando:

    mvn javadoc:javadoc

Essi verranno prodotti all'interno del folder `target/site/apidocs`.
Possono essere visionati anche dal sito generato con il Maven Site Plugin, seguendo le istruzioni per la generazione del sito.

