# CASI D'USO
Il diagramma UML degli Use Cases permette di visualizzare le interazioni tra gli attori
(utenti o sistemi esterni come il TheGuardian) ed il sistema software.
Il diagramma rappresenta una visione ad alto livello dei requisiti funzionali del sistema 
e identifica i principali scenari o flussi di lavoro che coinvolgono gli utenti.

# Use cases in linguaggio naturale
- Un utente vuole sapere quali comandi può passare: passa il comando per l'aiuto, o non passa nulla,
  e il programma termina stampando a video le varie possibilità.
- Un utente vuole fare solo il download da una API: seleziona l'API desiderata,
  (eventualmente) inserisce la sua API key.
  Se queste non sono corrette, gli viene chiesto interattivamente di inserire il nome
  dell'API desiderata e i vari parametri.
  Ottiene un file in formato comune contenente tutti gli articoli desiderati (se non
  ha inserito ulteriori parametri, questi sar anno 1000 articoli con la query "nuclear power").
- Un utente vuole fare solo il "download" da una sorgente locale, ovvero da un folder
  che contiene un insieme di file in diversi formati: inserisce quindi il path del folder.
  Se questo non esiste, il programma crea un file serializzato vuoto.
  Ottiene un file in formato comune contenente tutti gli articoli del folder che si trovavano
  in file deserializzabili dal programma.
- Un utente vuole fare solo l'estrazione dei termini: inserisce i suoi file
  nel formato comune nel folder di output e chiede di estrarre i 50 termini con piu' peso
  e di salvarli in un file output.txt, nello stesso folder.
  Se questo file in formato comune non esiste, viene terminato il programma.
- Un utente vuole fare entrambe le azioni:
  - se desidera eseguire il download da una API seleziona l'API desiderata,
    (eventualmente) inserisce la sua API key.
    Se queste non sono corrette, gli viene chiesto interattivamente di inserire il nome
    dell'API desiderata e i vari parametri.
  - se desidera fare il "download" da una sorgente locale inserisce il path del folder.
    Se questo non esiste, il programma crea un file serializzato vuoto, per poi terminare (non
    si può fare l'analisi di un insieme di articoli vuoti).

  Ottiene un file in formato comune contenente tutti gli articoli desiderati, e un ulteriore file
  con i termini più importanti estratti da tali articoli
- Un utente vuole vedere la documentazione: installa Maven, segue le istruzioni indicate in [generare il sito](../istruzioni/sito.html)
  e può visualizzare la documentazione sul sito, passando tra le varie pagine mediante il menù a lato.
  In mancanza di installazione prima della generazione del sito, esso non conterrà i reports dei test.
- Un utente vuole vedere i javadocs: installa il tool, segue le istruzioni indicate in [generare javadocs](../istruzioni/javadoc.html)
  e può visualizzare i javadocs all'interno della cartella target mediante un qualsivoglia browser.
- Un utente vuole modificare il programma: aggiunge classi, cambia file di properties di default, modifica le classi
  già esistenti e infine ottiene il programma modificato.

Sono stati omessi i casi d'uso con varie combinazioni di argomenti passati da riga di comando
(ad esempio per cambiare file di properties o il numero di parole da estrarre).

# Use cases diagram
I vari casi d'uso possono essere schematizzati con i seguenti modelli dei casi d'uso:
sono stati omessi i casi d'uso con varie combinazioni di argomenti passati da riga di comando
(ad esempio per cambiare file di properties o il numero di parole da estrarre).

<img src="../images/Use_cases_uml.jpg"/>

# Use cases tables
Gli use cases in linguaggio naturale strutturato sono rappresentati di seguito:
sono stati omessi i casi d'uso con varie combinazioni di argomenti passati da riga di comando
(ad esempio per cambiare file di properties o il numero di parole da estrarre).

<img src="../images/Use_cases_tables.jpg"/>


