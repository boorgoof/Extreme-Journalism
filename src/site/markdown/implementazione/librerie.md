# LIBRERIE UTILIZZATE
Viene data indicazione di quali funzioni sono state riutilizzate da librerie esistenti,
specificando la versione delle librerie e il motivo per cui sono state scelte queste librerie.

## Kong Unirest
Sono state valutate attentamente diverse alternative prima di arrivare alla scelta della libreria
Kong Unirest:
* [**com.mashape.unirest**](https://javadoc.io/doc/com.mashape.unirest/unirest-java/latest/index.html): la libreria utilizzata nel client messo a disposizione, matarrese.
  E' una versione precedente di Unirest, molto simile nell'utilizzo, ma non essendo aggiornata non è stata
  presa in considerazione: nel [maven central repository](https://mvnrepository.com/artifact/com.mashape.unirest/unirest-java/1.4.9)
  viene detto che nella versione più recente sono presenti 51 vulnerabilità da dipendenze.
* [**Okhttp**](https://square.github.io/okhttp/): più aggiornata della precedente, ma non possiede 
  un convertitore da JSON a Object nativo: per convertire si sarebbe dovuta ottenere la risposta sotto forma
  di String e convertirla tramite un ObjectMapper.
* [**UrlHttpConnection**](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html): 
  libreria nativa di Java 8 per le chiamate http: libreria supportata nativamente da Java, quindi molto valida,
  tuttavia molto più a basso livello, e come per okhttp si sarebbe dovuto fare il doppio passaggio da String a Object.
  L'alternativa per Java da 11 in su è [HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html),
  molto interessante, ma dato che nei requisiti si consigliava di utilizzare Java 8, è stata scartata.

Libreria scelta: **kong.unirest**, molto aggiornato, contiene anche un ObjectMapper di default per JSON (basato su GSON),
e permette con facilità di salvare la risposta su un file.

## Jackson


## Apache Commons CLI
