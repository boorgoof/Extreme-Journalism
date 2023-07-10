# PROGETTO EXTREME JOURNALISM
Repository del progetto finale di "Elementi di Ingegneria del Software".

## Struttura delle directories del progetto
* `src`: qui c'è il codice sorgente dell'applicazione e dei test;

* `database`: qui possono essere messi i file da deserializzare (sono gia' presenti i .csv del nytimes). 
  Questa directory viene copiata all'interno della directory target/database all'installazione dell'applicazione,
  quindi i file da deserializzare possono essere messi nella cartella database prima dell'installazione o
  all'interno di target/database una volta avvenuta l'installazione;

* `cmd`: qui sono presenti degli script per eseguire il jar con le dipendenze una volta installata l'applicazione.
  Sono presenti script sia per Windows (.bat) che per Unix, cioè Linux e MacOS (.sh).
  Ce ne sono quattro:
  * Uno per capire quali parametri possono essere specificati (`help.sh` o `help.bat`);
  * Uno per fare il download dall'API specificata e la serializzazione a formato comune (`download.sh` o `download.bat`).
    Questo usa il file di properties presente nella directory OUT_PROPERTIES per selezionare l'API e passarvi i parametri;
  * Uno per fare l'analisi degli articoli del folder ny_times_v2 presente nella directory DATABASE (`analysis.sh` o `analysis.bat`);
  * Uno per fare il download dall'API specificata, serializzare a formato comune e fare l'analisi degli articoli scaricati (`da.sh` o `da.bat`).
    Questo usa il file di properties presente nella directory `out_properties` per selezionare l'API e passarvi i parametri;

* `out_properties`: qui è presente il file `api.properties` che viene utilizzato dagli script di CMD per la fase di download.
  Puo' essere utilizzato dall'utente per specificare quale API chiamare (di default è impostato sull'API del theGuardian,
  che e' l'unica disponibile), passandola al jar nel seguente modo:

  
    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -da -apf ../out_properties/api.properties


## Installazione del progetto
Per installare il progetto basta posizionare la propria chiave per la theGuardian API nei seguenti file:
- `src/test/resources/trueApi.properties` (in api-key)
- `src/test/resources/trueApiTest.properties` (in api-key)
- `src/test/java/system_tests/api.properties` (in api-key)
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

## Esecuzione del progetto
Per eseguire il jar con le dipendenze, da riga di comando digitare:

    cd target


Per portarsi all'interno della directory `target`. Da qui digitare:
* Per avere informazioni su quali argomenti possono essere passati da riga di comando


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -h


* Per fare il download dall'API specificata (chiesta interattivamente all'utente) e la serializzazione a formato comune


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -d


* Per fare la serializzazione a formato comune dei file nel folder il cui path è specificato in value


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -d -path [value]


* Per fare l'analisi degli articoli del file serializzato con un certo path


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -a -path [path]


* Per fare il download dall'API specificata (chiesta interattivamente all'utente), la serializzazione a formato comune e l'analisi degli articoli


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -da


* Per fare la serializzazione a formato comune dei file nel folder il cui path è specificato in value e l'analisi degli articoli


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -da


Alcune delle caratteristiche del programma sono modificabili andando a modificare i seguenti file, presenti in `src/main/resources`:
- `download.properties` contiene la libreria con cui effettuare le chiamate alle API e i vari APIManager che gestiscono la logica di queste chiamate e dei loro parametri.
- `serializers.properties` contiene i serializzatori per i vari formati (attualmente solo XML, il formato comune adottato)
- `deserializers.properties` contiene i deserializzatori per i vari formati
- `analyze.properties` contiene la strategia con cui analizzare i vari articoli e quella con cui stamparli in un file di output (quest'ultima strategia è quella che decide il formato di output)
- `general.properties` contiene il numero di termini da estrarre (se non è specificato un altro valore da riga di comando, che prevale), e il formato comune in cui vengono salvati i file prima di farne l'analisi
- `english_stoplist.txt` contiene i termini da ignorare durante l'analisi degli articoli. Questa opzione può essere disabilitata da riga di comando.

Per andare a immettere un file di API properties, in modo da non avere le richieste interattive per l'API,
si può andare ad utilizzare il file `out_properties/api.properties`, immettendo la propria chiave per l'API del
TheGuardian e andando a modificare i parametri, se desiderato.

### Opzioni da riga di comando
Per eseguire il programma in maniera personalizzata sono disponibili varie opzioni da passare da riga di comando:
* Per eseguire una determinata azione (deve essere utilizzato uno solo di questi quattro comandi, non inserirlo significa
  far terminare il programma con un errore):
  * `-h` per ottenere informazioni su come eseguire il programma
  * `-d` per fare il download degli articoli e il loro salvataggio in formato comune.
    Se si vuole specificare un folder presente in locale, è obbligatorio specificare l'opzione `-path` con il path del folder contenente gli articoli di cui fare la serializzazione a formato comune.
  * `-a` per fare l'analisi degli articoli, ovvero l'estrazione dei termini piu' importanti. 
  * `-da` per fare il download degli articoli e il loro salvataggio in formato comune. Come per `-d`,
    se si vuole specificare un folder presente in locale, è obbligatorio specificare l'opzione `-path` con il path del folder contenente gli articoli di cui fare la serializzazione a formato comune
    e l'analisi.
* Altre opzioni facoltative per cambiare valori di default, che accettano un valore:
  * `-apf [value]` per utilizzare un file di properties (il cui path è passato come valore) da cui prendere il nome della API da chiamare
    e i vari parametri da passarci. Un esempio è il file `out_properties/api.properties`. 
    Se questo non viene specificato, verrà chiesto all'utente interattivamente quale API chiamare.
  * `-dowpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
    `download.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  * `-genpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
    `general.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  * `-despf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
    `deserializers.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  * `-serpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
    `serializers.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  * `-anapf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
    `analyze.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  * `-path [value]` per fare la serializzazione degli articoli contenuti in un folder il cui path è passato come valore.
  * `-n [value]` per specificare il numero di termini che si vuole avere nel file di output. Questo prevarrà su quanto specificato nel file
    `general.properties` solo se è un numero intero positivo.
  * `-stop [value]` per disabilitare (con false) o abilitare (con true) le stop words specificate nel file `english_stoplist.txt` o in quello passato in `-stopfile`. Di default il suo valore è true
  * `-stopfile [value]` per andare a utilizzare le stop words del file passato come value e non di `english_stoplist.txt`. Se il file è assente, viene usato quello di default.
  * `-setfi [value]` per abilitare (con true) o disabilitare (con false) la richiesta interattiva di quali fields analizzare degli articoli, nel caso in cui questo sia reso
    possibile dal deserializzatore fornito.

## Generazione del sito
Il sito viene generato con il Maven Site Plugin: il codice sorgente per la generazione del sito è nel folder `src/site`.
Le istruzioni per creare e rendere disponibile il sito sono:

    mvn site
    mvn site:run

Il sito sarà quindi disponibile al seguente indirizzo: [http://localhost:8080/](http://localhost:8080/)
Esso contiene anche i reports dei test, oltre alla documentazione del progetto.
I suoi vari file saranno disponibili all'interno del folder `target/site`

Per terminare l'esecuzione di site:run (una volta chiusa la pagina web) digitare due volte da riga di comando CTRL+C (da dove si era avviato il sito).

## Generazione dei javadocs
Per generare i javadocs, digitare da riga di comando:

    mvn javadoc:javadoc

Essi verranno prodotti all'interno del folder `target/site/apidocs`, e apribili con un qualsivoglia browser.
I javadoc sono stati prodotti anche per i test: per generarli, digitare da riga di comando:

    mvn javadoc:test-javadoc

Essi verranno prodotti all'interno del folder `target/site/testapidocs`, e apribili con un qualsivoglia browser.
Questi ultimi sono molto più semplici di quelli del programma in sè, in quanto sarebbe stato impossibile
descrivere tutti i test effettuati all'interno di ogni classe per ogni funzione.