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
  Tuttavia l'interpretazione del file segue i seguenti vincoli.
  Il programma, di default, riconosce che un articolo è presente all'interno del file a partire dalla presenza di questi campi 
  {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" }. In particolare, un articolo è definito solamente se è presente il campo
  "id" all'interno del file. Gli altri, invece, sono opzionali.Nel caso in cui mancasse l'id" allora gli altri campi, se presenti, verrebbero completamente ignorati. 
  In sistesi, il programma interpreta la presenza di un articolo in questo modo: identifica il campo "id" e successivamente ricerca i successivi campi opzionali ("apiUrl", "headline", "bodyText", 
  "webPublicationDate", "publication", "sectionName"). 
  
  Ovviamente è possibile modificare tali campi presenti di default ad inizio programma (collegamento da fare). Se si seleziona l'opzione corretta il programma chiederà all'utente 
  di inserire un campo alla volta. Il primo inserito sarà il campo "fondamentale" che definisce la presenza dell'articolo nel file.

  Si consideri il seguente esempio:
  ``` 
  {
  "object": {
    "data": [
      {
        "response1": {
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
        "response2": {
          "id": "ID 2",
          "apiUrl": "URL 2",
          "headline": "Titolo 2",
          "bodyText": "Corpo 2",
          "webPublicationDate": "Data 2",
          "publication": "sourceSet 2",
          "sectionName": "source 2"
        },
        "response3": {
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
Si noti che la chiave "id" non è presente in response3. Segue che all'interno di questo codice JSON il programma individua solamente due articoli.
In particolare verranno costruiti due oggetti Article con il il seguente stato: 
  ```
   id = ID 1, url = URL 1, title = Titolo 1, body = Corpo 1, date = Data 1, sourceSet = sourceSet 1, source = source 1;
   id = ID 2, url = URL 2, title = Titolo 2, body = Corpo 2, date = Data 2, sourceSet = sourceSet 2, source = source 2;
  ```
## FILE CSV


Nel diagramma delle classi tutte le
associazioni hanno un nome e cardinalità? Le classi hanno gli
attributi necessari?