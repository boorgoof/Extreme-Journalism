# LIBRERIE UTILIZZATE
Viene data indicazione di quali funzioni sono state riutilizzate da librerie esistenti,
specificando la versione delle librerie e il motivo per cui sono state scelte queste librerie.

## Kong Unirest
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

Libreria scelta: **kong.unirest**, molto aggiornato, contiene anche un ObjectMapper di default per JSON (basato su GSON),
e permette con facilità di salvare la risposta su un file.

## Apache Commons CSV
[**Apache Commons CSV**](https://commons.apache.org/proper/commons-csv/apidocs/index.html) fornisce funzionalità per l'elaborazione di file CSV in Java, ossia
offre diverse classi utili per la lettura, la scrittura e la manipolazione dei dati CSV. Sono state usate queste classi per
la deserializzazione del formato csv:

- [**CsvFormat**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  Viene utilizzata per definire il formato del file CSV, specificando le opzioni di delimitazione, citazione ed escape.
- [**CSVParser**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  La classe CSVParser viene utilizzata per analizzare un file CSV in un'istanza di CSVRecord, che rappresenta una riga del file CSV.
  [**CSVRecord**](https://commons.apache.org/proper/commons-csv/apidocs/index.html):
  CSVRecord fornisce metodi per accedere ai valori delle colonne in base all'indice o al nome delle colonne.

Dunque, CSVParser viene utilizzata per analizzare un file CSV o una stringa CSV. Essa restituisce oggetti CSVRecord che rappresentano le righe del CSV.
E' possibile utilizzare vari metodi di un CSVRecord per accedere ai valori delle colonne nella riga del CSV.
CSVFormat viene utilizzata precedentemente per definire il formato del file CSV, specificando le opzioni di delimitazione, citazione ed escape.

## Jackson

[**Jackson**](https://fasterxml.github.io/jackson-databind/javadoc/2.12/) è una libreria Java ad alte prestazioni che fornisce funzionalità per l'elaborazione di dati JSON.
È progettata per consentire la conversione di oggetti Java in formato JSON, e viceversa. Sebbene la sua funzionalità principale 
sia focalizzata su JSON, Jackson include moduli aggiuntivi, come "Jackson Dataformat XML", che consentono di lavorare con dati XML.

- [**ObjectMapper**](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/ObjectMapper.html):
  Si tratta di una classe fornta da Jackson che permette la conversione tra oggetti Java e JSON. Nel programma viene utilizzata per la deserializzazione dei file JSON in oggetti Java. 
  Nello specifico,viene utilizzato un oggetto ObjectMapper per ottenere un un oggetto  [**JsonNode**](https://fasterxml.github.io/jackson-databind/javadoc/2.12/com/fasterxml/jackson/databind/JsonNode.html).
  Tale classe offre funzionalità per l'elaborazione, la manipolazione di dati JSON. 
  JsonNode può essere utilizzato per la rappresentazione ad albero dei dati JSON, consentendo un accesso e navigazione agevole all'interno di tale struttura.

- [**XmlMapper**](https://fasterxml.github.io/jackson-dataformat-xml/javadoc/2.13/com/fasterxml/jackson/dataformat/xml/XmlMapper.html): 
  Questa classe fornisce metodi per la conversione di oggetti Java in una rappresentazione XML e viceversa. 
  Offre funzionalità di associazione dati per mappare gli attributi e gli elementi XML agli attributi degli oggetti Java. 
  XmlMapper supporta diverse opzioni di configurazione e formattazione per l'output XML. 
  - Per quanto riguarda la deserializzazione sono state seguite le informazioni nella documentazione presenti in [**Jackson Databind**](https://github.com/FasterXML/jackson-databind)
  - Per quanto riguarda la Serializzazione, l'oggetto XmlMapper stato usato in combinazione con 
   [**SerializationFeaturer**](https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/SerializationFeature.html) 
    che consente di abilitare o disabilitare diverse funzionalità di serializzazione, come l'indentazione dell'output XML



## Apache Commons CLI
