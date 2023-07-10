# DESIGN PATTERN UTILIZZATI
Elenco dei design pattern utilizzati, e di dove questi sono stati adoperati, oltre alla motivazione.

## Singleton
* Nome: Singleton
* Problem: È ammessa esattamente una istanza della classe; è un
  singleton. E’ necessario un singolo punto di accesso globale
* Soluzione: Definire un metodo statico della classe che restituisce
  il singleton.
* Utilizzo: questo pattern è utilizzato per i `Container` della parte di download da una API,
  deserializzazione e serializzazione. Questo perché l'inizializzazione di tali container avviene
  leggendo un file di properties esterno, il cui valore però non cambia durante l'esecuzione del programma
  (l'utente può passare un diverso file di properties, ma questo verrà letto all'inizio del programma),
  rendendo quindi inutile l'inizializzazione multipla di questi container, i quali conterranno sempre le
  stesse informazioni.
  Per ovviare a tale problema, è stato utilizzato il Singleton design pattern per:
    * `APIContainer`, che contiene gli `APIManager` e viene inizializzato leggendo le `download.properties`
    * `DeserializersContainer`, che contiene i `Deserializer` e viene inizializzato leggendo le `deserializers.properties`
    * `SerializersContainer`, che contiene i `Serializer` e viene inizializzato leggendo le `serializers.properties`

## Strategy
* Nome: Strategy
* Problema: Come ammettere vari, ma affini,
  algoritmi/policy/strategie? Come progettare la possibilità di
  modificare tali algoritmi/policy/strategie?
* Soluzione (consiglio): Definire ciascun algoritmo/policy/strategia
  in una classe separata con un’interfaccia comune
* Utilizzo: questo pattern è utilizzato nella parte di analisi, e precisamente per `Analyzer` e `OutPrinter`,
  con l'intento di aumentare la [flessibilità](flessibilita.html) nei confronti di future modifiche:
    * `OutPrinter`: la logica attuale del programma impone che vengano stampati i
    50 termini più importanti su un file output.txt, come definito in `TxtOutPrinter`, ma in futuro potrebbero cambiare i
    requisiti e potrebbe essere necessaria la stampa su un altro tipo di file, o su altre strutture.
    In quel caso, le uniche cose da cambiare sarebbero l'implementazione di una nuova classe che implementi `OutPrinter`
    e il cambiamento del file di properties.
    * `Analyzer`: attualmente la classe `MapSplitAnalyzer` estrae i tot termini più importanti e li pone in una lista
    da ritornare all'handler, ma questa logica potrebbe cambiare (si potrebbero voler estrarre i tot termini
    meno importanti), quindi come prima sarebbe molto semplice aggiungere una nuova classe e cambiare il file di properties.

## Adapter
* Nome: Adapter (anche conosciuto come Wrapper)
* Problema: Come risolvere l’incompatibilità tra interfacce o fornire
  un’interfaccia stabile verso componenti simili ma con diverse
  interfacce?
* Soluzione (consiglio): Convertire l’interfaccia originale di un
  componente in un’altra interfaccia, mediante un oggetto adapter
  intermedio.
* Utilizzo: questo pattern è stato utilizzato per l'implementazione di varie interfacce
  con l'intento di aumentare la [flessibilità](flessibilita.html) nei confronti di future modifiche.
  Più precisamente, esso può essere ritrovato in:
    * `APICaller` e la sua implementazione `KongAPICaller`: questo è un adapter tra gli `APIManager` e le
    librerie che vanno effettivamente a fare le chiamate alle API: i primi dovranno solo utilizzare la funzione
    `sendRequest()`, passando i vari parametri ma senza preoccuparsi di come le diverse librerie vadano ad implementare quella funzione.
    * `APIManager` e la sua implementazione `GuardianAPIManager`: questo è un adapter tra `APIContainer`, e in generale
    l'utente che vuole selezionare una API in modo standard, e la logica che sta dietro al controllo dei parametri e alla 
    creazione delle chiamate da parte dell'`APIManager`.

## Facade
* Nome: Facade
* Problema: È richiesta un’interfaccia comune ed unificata verso un
  insieme di implementazioni o interfacce eterogenee – ad esempio
  in un sottosistema. Ci potrebbe essere un accoppiamento non
  desiderato con molteplici parti del sottosistema, o
  l’implementazione del sottosistema potrebbe cambiare.
* Soluzione (consiglio): Definire un singolo punto di contatto con il
  sottosistema – un oggetto facade che avvolga (wrap) il
  sottosistema; tale oggetto presenta un’unica interfaccia unificata
  ed è responsabile per l’interazione con i componenti del
  sottosistema.
* Utilizzo: questo pattern è utilizzato in tutti gli `Handler` dei vari sottosistemi,
  quindi `DownloadHandler`, `AnalyzerHandler`, `DeserializationHandler`, `SerializationHandler`,
  i quali gestiscono in maniera autonoma la complessità dei loro sottosistemi, fornendo al main
  una interfaccia attraverso la quale andare a comunicare coi vari sottosistemi, ma nascondendo le varie
  complessità (rendendo il tutto anche molto meno accoppiato, in favore del low coupling).
