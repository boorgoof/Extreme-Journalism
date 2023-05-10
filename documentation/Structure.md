# Interface with Guardian API
* Come fare richieste HTTP
https://www.baeldung.com/java-9-http-client
https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html#ofFile(java.nio.file.Path)
https://javadoc.io/doc/com.mashape.unirest/unirest-java/latest/index.html
  
# Serializzatore
* Li mettiamo tutti in un unico XML
  * article
    * title
    * /title
    * body
    * /body
  * /article

# Deserializzatore e cerca termini
* Mappa (la parola è la key). L'alternativa è la priority queue (il numero di occorrenze è la key), 
  ma bisogna vedersela bene perchè non so come si faccia ad estrarre la chiave di quella introdotta.
  In caso si puo' creare una propria AdaptablePriorityQueue.