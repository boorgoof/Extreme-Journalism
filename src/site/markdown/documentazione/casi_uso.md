Esempio di informazioni da includere in uno schema:
1. descrizione funzione o entità che si sta specificando
2. input e origini degli input
3. output e destinazioni degli output
4. dati necessari (e.g. per i calcoli)
5. azione da eseguire
6. se metodo funzionale, pre-condizione da verificarsi perché la
   funzione venga chiamata, e post-condizione dopo chiamata
   funzione
7. descrizione degli effetti collaterali dell’operazione, se presenti

Gli use case rappresentati con diagrammi
UML e con descrizione in linguaggio naturale strutturato.

SONO SCENARI:
Scenario include:
∙ descrizione di ciò che il sistema e gli utenti si aspettano quando
inizia lo scenario
∙ descrizione del flusso normale di eventi nello scenario
∙ descrizione di cosa puó causare errori e come possono essere
gestiti i problemi risultanti
∙ informazioni su altre attività che potrebbero svolgersi
contemporaneamente
∙ descrizione dello stato del sistema al termine dello scenario

## Casi d'uso
- Un utente vuole fare solo il download: viene svuotato il database, seleziona che vuole
  estrarre dal TheGuardian, inserisce la sua API key, inserisce un insieme di parole
  chiave tra cui cercare, aspetta il download di 1000 articoli e la loro trasformazione
  in un formato comune.
- Un utente vuole fare solo l'estrazione dei termini: inserisce i suoi file
  nel formato comune nel database e chiede di estrarre i 50 termini con piu' peso
  e di salvarli in un file .txt di cui specifica il nome.
- Un utente vuole fare entrambe le azioni: viene svuotato il database, seleziona che vuole
  estrarre dal TheGuardian, inserisce la sua API key, inserisce un insieme di parole
  chiave tra cui cercare, aspetta il download di 1000 articoli e la loro trasformazione
  in un formato comune, chiede di estrarre i 50 termini con piu' peso
  e di salvarli in un file .txt di cui specifica il nome.

## FILE JSON  

  Il programma è in grado di gestire un file JSON con varie strutture: come oggetti JSON con chiavi e valori, array JSON con elementi multipli e oggetti annidati all'interno di array.
  L'interpretazione del file segue i seguenti vincoli:
  Il programma, di default, riconosce che un articolo è presente all'interno del file a partire dalla presenza di questi campi 
  {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" }. In particolare, un articolo è definito solamente se è presente il campo
  "id" all'interno del file. Gli altri, invece, sono opzionali. Nel caso in cui mancasse l'id" allora gli altri campi, se presenti, verrebbero completamente ignorati. 
  In sistesi, il programma interpreta la presenza di un articolo in questo modo: identifica il campo "id" e successivamente ricerca i successivi campi opzionali ("apiUrl", "headline", "bodyText", 
  "webPublicationDate", "publication", "sectionName"). 
  
  Ovviamente è possibile modificare tali campi presenti di default ad inizio programma (collegamento da fare). Se si seleziona l'opzione corretta il programma chiederà all'utente 
  di inserire un campo alla volta. Il primo inserito sarà il campo "fondamentale" che definisce la presenza dell'articolo nel file. Nel caso in cui si vogliano inserire meno campi, allora
  il programma indicherà di inserire una stringa vuota.

  Si consideri il seguente esempio:
  ``` 
{
  "oggetto": {
    "dati": [
      {
        "risposta1": {
          "id": "ID 1",
          "apiUrl": "URL 1",
          "headline": "Titolo 1",
          "bodyText": "Corpo 1",
          "webPublicationDate": "Data 1",
          "publication": "sourceSet 1",
          "sectionName": "source 1"
        }
      },
      {
        "risposta2": {
          "id": "ID 2",
          "apiUrl": "URL 2",
          "headline": "Titolo 2",
          "bodyText": "Corpo 2",
          "webPublicationDate": "Data 2",
          "publication": "sourceSet 2",
          "sectionName": "source 2"
        },
        "risposta3": {
          "apiUrl": "URL 3",
          "headline": "Titolo 3",
          "bodyText": "Corpo 3",
          "webPublicationDate": "Data 3",
          "publication": "sourceSet 3",
          "sectionName": "source 3"
        }
      }
    ]
  }
}

  ```
Si noti che la chiave "id" non è presente in response3. Segue che all'interno di questo codice JSON, il programma individuerà solamente due articoli.
In particolare verranno costruiti due oggetti Article con il seguente stato: 
  ```
   id = "ID 1", url = "URL 1", title = "Titolo 1", body = "Corpo 1", date = "Data 1", sourceSet = "sourceSet 1", source = "source 1";
   id = "ID 2", url = "URL 2", title = "Titolo 2", body = "Corpo 2", date = "Data 2", sourceSet = "sourceSet 2", source = "source 2";
  ```
## FILE CSV
 La deserializzazione CSV richiede che sia presente una intestazione (header) che definisce il contenuto di ciascuna colonna 
 del file. Il programma terrà a considerazione le colonne che hanno una corrispondenza con il seguente header di default:
 {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"}

 1) Esempio 

  ```
  URL,Identifier,Title,Body,Source Set,Source,Cover
  URL 1,ID 1,Title 1,Body 1,SourceSet 1,Source 1,cover 1
  URL 2,ID 2,Title 2,Body 2,SourceSet 2,Source 2,cover 2
  URL 3,ID 3,Title 3,Body 3,SourceSet 3,Source 3,cover 3
  ```
  In tale esempio vengono indicati i campi "Identifier", "URL", "Title", "Body", "Source Set", "Source". Tuttavia, si noti che è mancante il campo "Date".
  Segue che verranno deserializzati 3 articoli (sono presenti 3 righe) che avranno il campo "date" inizializzato a null. Gli articoli avranno quindi il seguente stato:
  
```
   id = "ID 1", url = "URL 1", title = "Titolo 1", body = "Corpo 1", date = null, sourceSet = "sourceSet 1", source = "Source 1";
   id = "ID 2", url = "URL 2", title = "Titolo 2", body = "Corpo 2", date = null, sourceSet = "sourceSet 2", source = "Source 2";
   id = "ID 3", url = "URL 3", title = "Titolo 3", body = "Corpo 3", date = null, sourceSet = "sourceSet 3", source = "Source 3";
```

  2) Esempio
  ```
  Identifier,URL,Title,Body,Site,Date,Source Set,Source
  ID 1,URL 1,,Body 1,Site1,Date 1,SourceSet 1,Source 1
  ID 2,URL 2,Title 2,,Site 2,Date 2,SourceSet 2,Source 2
  ID 3,URL 3,Title 3,Body 3,Site 3,Date 3,,Source 3
  ```
  Nel caso in cui l'header sia specificato correttamente, ma il record corrispondente sia vuoto, l'oggetto avrà il campo corrispondente inizalizzato 
  con una stringa vuota. In questo esempio si ottengono oggetti con il seguente stato:

```
  id = "ID 1", url = "URL 1", title = "", body = "Corpo 1", date = null, sourceSet = "sourceSet 1", source = "Source 1";
  id = "ID 2", url = "URL 2", title = "Titolo 2", body = "", date = null, sourceSet = "sourceSet 2", source = "Source 2";
  id = "ID 3", url = "URL 3", title = "Titolo 3", body = "Corpo 3", date = null, sourceSet = "", source = "Source 3";
```
  Caratteristiche header :
  - Non può contenere caratteri differenti da lettere, numeri o spazi bianchi.
  - Può avere i campi in posizione differente da quelli memorizzati di default.
  - Può specificare meno campi rispetto a quelli memorizzati (Esempio 1: manca "date") 
  - Possono essere presenti nell'header dei campi supplementari che il programma ignorerà (Esempio 1: è presente "cover")
  
 Si ricorda che, se si deseridera, è possibile modificare l'header memorizzato di defualt per la deserializzazione ad inizo programma

## FILE XML 

  La deserializzazione di un file XML implementata nel programma non è flessibile come i precedenti casi. 
  
  Bisogna tenere in considerazione i seguenti vincoli:

  - La deserializzazione avviene correttamente solamente se il nome degli elementi è uguale al nome degli attributi della classe Article.
    Gli elementi devono pertanto avere nome uguale a {"id", "url", "title", "body", "date", "sourceSet", "source"}
  - Attualmente non è possibile deserializzare elementi differenti da quelli di default. Se ad inizio programma si richiede di modificare i campi 
    di deserializzazione del formato XML, verrà inoltrato un messaggio di errore.
  - Nel file gli articoli devono essere necessariamente specificati all'interno di una struttura ordinata di tipo array. Non è possibile avere altri elementi 
    che modificano tale struttura. Sono riportati quattro esempi esplicativi. 

1) Esempio
```
<ArrayList>
  <item>
    <id>ID 1</id>
    <url>URL 1</url>
    <title>Title 1</title>
    <body>Body 1</body>
    <date>Date 1</date>
    <sourceSet>sourceSet 1</sourceSet>
    <source>Source 1</source>
  </item>
  <item>
    <id>ID 2</id>
    <url>URL 2</url>
    <title>Title 2</title>
    <body>Body 2</body>
    <date>Date 2</date>
    <sourceSet>sourceSet 2</sourceSet>
    <source>Source 2</source>
  </item>
</ArrayList>
```
Articoli:
```
 articolo 1 avrà stato: ("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1")
 articolo 2 avrà stato: ("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
```

2) Esempio
```
<ArrayList>
  <item>
    <id>ID 1</id>
    <title>Title 1</title>
    <body>Body 1</body>
    <date>Date 1</date>
    <sourceSet>sourceSet 1</sourceSet>
  </item>
  <item>
    <id>ID 2</id>
    <url>URL 2</url>
    <body>Body 2</body>
    <date>Date 2</date>
    <sourceSet>sourceSet 2</sourceSet>
    <source>Source 2</source>
  </item>
</ArrayList>
```
Articoli:

```
articolo 1 avrà stato: ("ID 1", null, "Title 1", "Body 1", "Date 1","sourceSet 1",null)
articolo 2 avrà stato: ("ID 2", "URL 2", null, "Body 2", "Date 2","sourceSet 2","Source 2")
```
3) Esempio
```
<ArrayList>
  <item>
    <cover>cover 1</cover>
    <sourceSet>sourceSet 1</sourceSet>
    <source>Source 1</source>
    <id>ID 1</id>
    <url>URL 1</url>
    <title>Title 1</title>
    <body>Body 1</body>
    <date>Date 1</date>
  </item>
  <item>
    <id>ID 2</id>
    <url>URL 2</url>
    <title>Title 2</title>
    <sourceSet>sourceSet 2</sourceSet>
    <source>Source 2</source>
    <body>Body 2</body>
    <date>Date 2</date>
  </item>
</ArrayList>
```
Articoli:
```
articolo 1 avrà stato: ("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1"));
articolo 2 avrà stato: ("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
```
4) Esempio. Caso errato
```
<object>
  <data>
    <sector>Articles</sector>
    <type>science</type>
    <item>
      <response>
        <id>ID 2</id>
        <url>URL 2</url>
        <title>Title 2</title>
        <body>Body 2</body>
        <date>Date 2</date>
        <sourceSet>sourceSet 2</sourceSet>
        <source>Source 2</source>
      </response>
    </item>
    <item>
      <response>
        <id>ID 2</id>
        <url>URL 2</url>
        <title>Title 2</title>
        <body>Body 2</body>
        <date>Date 2</date>
        <sourceSet>sourceSet 2</sourceSet>
        <source>Source 2</source>
      </response>
    </item>
  </data>
</object>
```
  Come è possibile osservare, all'interno della stuttura dell'array gli elementi possono essere in posizione differente, possono essere mancanti, o possono essere presenti elementi supplementari.
  Tuttavia è importante notare che gli elementi supplementari non possono rompere la struttura di base.

  Si tratta di limitazioni importanti, ma attualmente il programma si aspetta di trattare con i formati CSV e JSON. La deserializzazione del formato XML
  è prevista solamente a livello interno, ovvero a partire dal file serialializzato per memorizzare in maniera ordinata tutti gli articoli
  (tale file rispetta sicuramente la stuttura elencata). Ovviamente se l'utente desidera può decidere di sottoporre i file XML che rispettano tali caratteristiche.


Nel diagramma delle classi tutte le
associazioni hanno un nome e cardinalità? Le classi hanno gli
attributi necessari?