# EIS-FINAL
Repository del progetto finale di "Elementi di Ingegneria del Software".

## Structure of the project's directories
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

* `out_properties`: qui è presente il file api.properties che viene utilizzato dagli script di CMD per la fase di download.
  Puo' essere utilizzato dall'utente per specificare quale API chiamare (di default è impostato sull'API del theGuardian,
  che e' l'unica disponibile), passandola al jar nel seguente modo:

  
    java -jar Extreme_journalism-1.0-jar-with-dependencies.jar -da -apf ../out_properties/api.properties


## INSTALL THE PROJECT
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

## EXECUTE THE PROJECT
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

## ARGOMENTI PASSABILI DA RIGA DI COMANDO

## SPIEGAZIONE