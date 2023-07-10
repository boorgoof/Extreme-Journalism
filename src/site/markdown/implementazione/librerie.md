# LIBRERIE UTILIZZATE
Viene data indicazione di quali funzioni sono state riutilizzate da librerie esistenti,
specificando la versione delle librerie e il motivo per cui sono state scelte queste librerie.

## Kong Unirest (Version: 3.14.2)
Sono state valutate attentamente diverse alternative prima di arrivare alla scelta della libreria
Kong Unirest:

- [**com.mashape.unirest**](https://javadoc.io/doc/com.mashape.unirest/unirest-java/latest/index.html): la libreria utilizzata nel client messo a disposizione, matarrese.
  E' una versione precedente di Unirest, molto simile nell'utilizzo, ma non essendo aggiornata non è stata
  presa in considerazione: nel [maven central repository](https://mvnrepository.com/artifact/com.mashape.unirest/unirest-java/1.4.9)
  viene detto che nella versione più recente sono presenti 51 vulnerabilità da dipendenze.

- [**Okhttp**](https://square.github.io/okhttp/): più aggiornata della precedente, ma non possiede 
  un convertitore da JSON a Object nativo: per convertire si sarebbe dovuta ottenere la risposta sotto forma
  di String e convertirla tramite un ObjectMapper.

- [**UrlHttpConnection**](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html): 
  libreria nativa di Java 8 per le chiamate http: libreria supportata nativamente da Java, quindi molto valida,
  tuttavia molto più a basso livello, e come per okhttp si sarebbe dovuto fare il doppio passaggio da String a Object.
  L'alternativa per Java da 11 in su è [HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html),
  molto interessante, ma dato che nei requisiti si consigliava di utilizzare Java 8, è stata scartata.

Libreria scelta: [**kong.unirest**](https://kong.github.io/unirest-java/), molto aggiornato, contiene anche un ObjectMapper di default per JSON (basato su GSON),
e permette con facilità di salvare la risposta su un file.
E' stata scelta anche perché in caso si volesse ottenere un oggetto e non un file, le modifiche da fare sarebbero minime.
Purtroppo fornisce dei warning in fase di chiamata, dovuti al setting dei cookie, ma questo viene risolto
direttamente nel costruttore ignorando questi cookie.

## Apache Commons CSV (Version: 1.10.0)
[**Apache Commons CSV**](https://commons.apache.org/proper/commons-csv/apidocs/index.html) fornisce funzionalità per l'elaborazione di file CSV in Java, ossia
offre diverse classi utili per la lettura, la scrittura e la manipolazione dei dati CSV. Sono state usate queste classi per
la deserializzazione del formato csv:

- [**CsvFormat**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  viene utilizzata per definire il formato del file CSV, specificando le opzioni di delimitazione ed escape.
- [**CSVParser**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  viene utilizzata per analizzare un file CSV in un'istanza di CSVRecord, che rappresenta una riga del file CSV.
- [**CSVRecord**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  fornisce metodi per accedere ai valori delle colonne in base all'indice o al nome delle colonne.

Dunque, è una classe CSVParser viene utilizzata per analizzare un file CSV o una stringa CSV. Essa restituisce oggetti CSVRecord che rappresentano le righe del CSV.
E' possibile utilizzare vari metodi di un CSVRecord per accedere ai valori delle colonne nella riga del CSV.
CSVFormat viene utilizzata precedentemente per definire il formato del file CSV, specificando le opzioni di delimitazione ed header del file.

## Jackson (Version: 2.15.0 databind, 2.15.1 dataformat)

[**Jackson**](https://fasterxml.github.io/jackson-databind/javadoc/2.12/) è una libreria Java ad alte prestazioni che fornisce funzionalità per l'elaborazione di dati JSON.
È progettata per consentire la conversione di oggetti Java in formato JSON, e viceversa. Sebbene la sua funzionalità principale 
sia focalizzata su JSON, Jackson include moduli aggiuntivi che consentono di lavorare con dati XML.

- [**ObjectMapper**](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/ObjectMapper.html):
  Si tratta di una classe fornta da Jackson che permette la conversione tra oggetti Java e JSON. Nel programma viene utilizzata per la deserializzazione dei file JSON in oggetti Java. 
  Nello specifico,viene utilizzato un oggetto ObjectMapper per ottenere un un oggetto  [**JsonNode**](https://fasterxml.github.io/jackson-databind/javadoc/2.12/com/fasterxml/jackson/databind/JsonNode.html).
  Tale classe offre funzionalità per l'elaborazione e la manipolazione di dati JSON. 
  JsonNode può essere utilizzato per la rappresentazione ad albero dei dati JSON, consentendo un accesso e navigazione agevole all'interno di tale struttura.

- [**XmlMapper**](https://fasterxml.github.io/jackson-dataformat-xml/javadoc/2.13/com/fasterxml/jackson/dataformat/xml/XmlMapper.html): 
  Questa classe fornisce metodi per la conversione di oggetti Java in una rappresentazione XML e viceversa. 
  Offre funzionalità di associazione dati per mappare gli attributi e gli elementi XML agli attributi degli oggetti Java. 
  XmlMapper supporta diverse opzioni di configurazione e formattazione per l'output XML. 
    - Per quanto riguarda la deserializzazione sono state seguite le informazioni presenti nella documentazione di [**Jackson Databind**](https://github.com/FasterXML/jackson-databind)
    - Per quanto riguarda la Serializzazione, l'oggetto XmlMapper è stato usato in combinazione con 
    [**SerializationFeaturer**](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/SerializationFeature.html) 
    che consente di abilitare o disabilitare diverse funzionalità di serializzazione, come l'indentazione dell'output XML


## Apache Commons CLI (Version: 1.5.0)
[Documentazione](https://commons.apache.org/proper/commons-cli/)

Questa libreria è utilizzata per fare il parsing dei comandi impartiti dall'utente da riga di comando
all'esecuzione del programma, e anche per stampare a video gli errori dovuti a questa cosa e le varie opzioni possibili.
L'utilizzo di questa libreria, interamente riportato all'interno di `CommandLineInterpreter`, consiste di 3 fasi:

1. Definizione di alcune opzioni che l'utente potrà fornire da riga di comando, elencate in [esecuzione](../istruzioni/esecuzione.html).

2. Parsing delle opzioni fornite dall'utente (in questo caso mediante un parser di default):
   in maniera intelligente in mancanza di almeno una delle opzioni definite necessarie, viene lanciata un'eccezione

3. Interrogazione dell'oggetto fornito dal parsing per ottenere i valori specificati con ogni opzione, o la
   presenza o meno di una certa opzione.
