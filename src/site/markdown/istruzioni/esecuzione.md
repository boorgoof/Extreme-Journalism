# Esecuzione del progetto
Per eseguire il jar con le dipendenze, da riga di comando digitare:

    cd target


Per portarsi all'interno della directory `target`. Da qui digitare:
* Per avere informazioni su quali argomenti possono essere passati da riga di comando


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -h


* Per fare il download dall'API specificata (chiesta interattivamente all'utente) e la serializzazione a formato comune


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -d


* Per fare l'analisi degli articoli di un certo folder con un certo path


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -a -path [path]


* Per fare il download dall'API specificata (chiesta interattivamente all'utente), la serializzazione a formato comune e l'analisi degli articoli


    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -da

Alcune delle caratteristiche del programma sono modificabili andando a modificare i seguenti file, presenti in `src/main/resources`:
- `download.properties` contiene la libreria con cui effettuare le chiamate alle API e i vari APIManager che gestiscono la logica di queste chiamate e dei loro parametri.
- `serializers.properties` contiene i serializzatori per i vari formati (attualmente solo XML, il formato comune adottato)
- `deserializers.properties` contiene i deserializzatori per i vari formati
- `analyze.properties` contiene la strategia con cui analizzare i vari articoli e quella con cui stamparli in un file di output (quest'ultima strategia è quella che decide il formato di output)
- `general.properties` contiene il numero di termini da estrarre (se non è specificato un altro valore da riga di comando, che prevale), e il formato comune in cui vengono salvati i file prima di farne l'analisi
- `english_stoplist.txt` contiene i termini da ignorare durante l'analisi degli articoli. Questa opzione può essere disabilitata da riga di comando.

### Opzioni da riga di comando

Per eseguire il programma in maniera personalizzata sono disponibili varie opzioni da passare da riga di comando:

- Per eseguire una determinata azione (deve essere utilizzato uno solo di questi quattro comandi, non inserirlo significa
  far terminare il programma con un errore):
    - `-h` per ottenere informazioni su come eseguire il programma
  
    - `-d` per fare il download degli articoli e il loro salvataggio in formato comune
  
    - `-a` per fare l'analisi degli articoli, ovvero l'estrazione dei termini più importanti.
  
      In questo caso è obbligatorio specificare l'opzione `-path` con il path del folder contenente gli articoli di cui fare l'analisi
  
    - `-da` per fare il download degli articoli e il loro salvataggio in formato comune

- Altre opzioni facoltative per cambiare valori di default, che accettano un valore:

    - `-apf [value]` per utilizzare un file di properties (il cui path è passato come valore) da cui prendere il nome della API da chiamare
      e i vari parametri da passarci. Un esempio è il file `out_properties/api.properties`.
      Se questo non viene specificato, verrà chiesto all'utente interattivamente quale API chiamare.
  
    - `-dowpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
      `download.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  
    - `-genpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
      `general.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  
    - `-despf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
      `deserializers.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  
    - `-serpf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
      `serializers.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  
    - `-anapf [value]` per utilizzare un file di properties (il cui path è passato come valore) al posto del file di default
      `analyze.properties`. Questo file verrà utilizzato solo se esistente e non vuoto, se contiene degli errori il programma terminerà.
  
    - `-path [value]` per fare l'analisi degli articoli contenuti in un folder il cui path è passato come valore. Se viene specificato con
      l'opzione `-da`, verrà fatta l'analisi di questi articoli, non di quelli scaricati.
  
    - `-n [value]` per specificare il numero di termini che si vuole avere nel file di output. Questo prevarrà su quanto specificato nel file
      `general.properties` solo se è un numero intero positivo.
  
    - `-stop [value]` per disabilitare (con false) o abilitare (con true) le stop words specificate nel file `english_stoplist.txt`. Di default il suo valore è true
  
    - `-setfi [value]` per abilitare (con true) o disabilitare (con false) la richiesta interattiva di quali fields analizzare degli articoli, nel caso in cui questo sia reso
      possibile dal deserializzatore fornito.


### Opzioni da riga di comando (HO LASCIATO MA é DA ELIMINARE)
Per eseguire il programma in maniera personalizzata sono disponibili varie opzioni da passare da riga di comando:
* Per eseguire una determinata azione (deve essere utilizzato uno solo di questi quattro comandi, non inserirlo significa
  far terminare il programma con un errore):
    * `-h` per ottenere informazioni su come eseguire il programma
    * `-d` per fare il download degli articoli e il loro salvataggio in formato comune
    * `-a` per fare l'analisi degli articoli, ovvero l'estrazione dei termini piu' importanti.
      In questo caso è obbligatorio specificare l'opzione `-path` con il path del folder contenente gli articoli di cui fare l'analisi
    * `-da` per fare il download degli articoli e il loro salvataggio in formato comune
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
    * `-path [value]` per fare l'analisi degli articoli contenuti in un folder il cui path è passato come valore. Se viene specificato con
      l'opzione `-da`, verrà fatta l'analisi di questi articoli, non di quelli scaricati.
    * `-n [value]` per specificare il numero di termini che si vuole avere nel file di output. Questo prevarrà su quanto specificato nel file
      `general.properties` solo se è un numero intero positivo.
    * `-stop [value]` per disabilitare (con false) o abilitare (con true) le stop words specificate nel file `english_stoplist.txt`. Di default il suo valore è true
    * `-setfi [value]` per abilitare (con true) o disabilitare (con false) la richiesta interattiva di quali fields analizzare degli articoli, nel caso in cui questo sia reso
      possibile dal deserializzatore fornito.
