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

Libreria scelta: [**kong.unirest**](https://kong.github.io/unirest-java/), molto aggiornato, contiene anche un ObjectMapper di default per JSON (basato su GSON),
e permette con facilità di salvare la risposta su un file.
E' stata scelta anche perché in caso si volesse ottenere un oggetto e non un file, le modifiche da fare sarebbero minime.
Purtroppo fornisce dei warning in fase di chiamata, dovuti al setting dei cookie, ma questo viene risolto
direttamente nel costruttore ignorando questi cookie.

## Jackson


## Apache Commons CLI
[Documentazione](https://commons.apache.org/proper/commons-cli/)

Questa libreria è utilizzata per fare il parsing dei comandi impartiti dall'utente da riga di comando
all'esecuzione del programma, e anche per stampare a video gli errori dovuti a questa cosa e le varie opzioni possibili.
L'utilizzo di questa libreria, interamente riportato all'interno di `CommandLineInterpreter`, consiste di 3 fasi:
1. Definizione di alcune opzioni che l'utente potrà fornire da riga di comando, elencate in [esecuzione](../istruzioni/esecuzione.html).
2. Parsing delle opzioni fornite dall'utente (in questo caso mediante un parser di default):
   in maniera intelligente in mancanza di almeno una delle opzioni definite necessarie, viene lanciata un'eccezione
3. Interrogazione dell'oggetto fornito dal parsing per ottenere i valori specificati con ogni opzione, o la
   presenza o meno di una certa opzione.
