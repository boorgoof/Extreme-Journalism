# PANORAMICA AD ALTO LIVELLO DEL PROGETTO
Breve spiegazione di come sono state implementate le funzionalità richieste, e quali
assunzioni sono state fatte.
Per avere una visione d'insieme delle classi utilizzate e delle loro relazioni 
vedere [design model](../documentazione/design_model.md).

Il codice è interamente in inglese, per una maggiore flessibilità riguardo possibili modifiche, che
potrebbero essere fatte da chiunque.

## DOWNLOAD
### Download da API
- **Handler:** come ogni sottosistema di questo programma, anche la parte di download da una API è gestita
interamente da un [handler](design_patterns.md), il quale nasconde al main la logica della chiamata e ne fornisce
una semplice modalità di chiamata.

- **API properties:** per selezionare l'API si può passare un file di properties contenente il nome dell'API, come
definito nel file di download properties, e i vari parametri per la chiamata. Questo è fatto per non dover scrivere
ogni volta i vari parametri a mano, ma non esiste un file di properties di default perché limiterebbe le possibilità dell'utente
nel caso in cui non avesse accesso al codice sorgente.

- **Selezione interattiva dell'API:** un'altra modalità per selezionare l'API è interattivamente: all'utente vengono mostrate
le possibili API da chiamare e gli viene detto di scegliere quale chiamare e quali parametri passarvi.
Questa selezione interattiva avviene se l'utente non ha specificato un folder presente in locale come sorgente da cui fare la serializzazione
e se non è stato fornito un file di API properties o questo è invalido. Questa richiesta interattiva utilizza la riga di comando
e continua finché non viene selezionata correttamente una API.

- **Download properties:** per garantire una maggiore [flessibilità](flessibilita.md) vengono utilizzati dei file di properties.
Il file di properties di download contiene l'APICaller con cui chiamare le API e i vari APIManager con i nomi delle API che implementano.
Esiste un file di properties di default, contenuto dentro alle risorse, ma è anche possibile passarne uno dall'esterno, in caso non si
avesse accesso al codice sorgente. Se le properties che l'utente vuole utilizzare sono incorrette, il programma termina.

- **APIContainer:** è la classe che contiene tutti gli APIManager specificati nel file download.properties o nel file
passato dall'utente (se valido). Utilizza il [Singleton design pattern](design_patterns.md) in modo da leggere il file di
properties una sola volta durante l'esecuzione del programma (in effetti, questo non può cambiare durante l'esecuzione del programma),
nonostante venga acceduto da diverse parti del codice.

- **APICaller:** per chiamare la vera e propria API vengono utilizzate delle librerie: ogni classe che utilizzi la logica
di una libreria per permettere di fare richieste HTTP alle varie API deve implementare l'interfaccia APICaller.
Questa interfaccia di fatto rappresenta un Adapter (vedi [design patterns](design_patterns.md)) tra le class che
gestiscono la logica dei parametri per le varie API e il server che deve ricevere queste richieste.
E' quindi molto semplice, come spiegato in [flessibilità](flessibilita.md), utilizzare un'altra libreria per fare
queste richieste al posto di quella da noi scelta.
E' stato scelto di salvare i file di risposta dall'API, invece che ottenere direttamente degli oggetti:
questo per uniformare la chiamata dell'API con il "download" di file presenti in locale e messi in un certo folder
dall'utente stesso. In entrambi i casi, infatti, il controllo passerà poi alla parte di deserializzazione.

- **APIManager:** un APIManager è una classe che ha la logica per contenere e verificare i parametri passati all'API.
Questa interfaccia viene utilizzata per presentare le varie API all'utente, con i loro nomi e i possibili parametri, oltre
che per aggiungere parametri per la chiamata (e quindi verificarli) e chiamare effettivamente l'API con i parametri specificati.
Essendo un'interfaccia, non è possibile definire un costruttore da implementare, ma ogni APIManager dovrebbe avere un
costruttore come definito all'interno dei javadocs dell'interfaccia.
Sono degli [adapter](design_patterns.md) per le varie API che è possibile chiamare.
Tutti i manager vengono contenuti all'interno di un APIContainer, e l'insieme dei manager viene preso dal file download.properties,
permettendo quindi [flessibilità](flessibilita.md) per l'aggiunta di future sorgenti.
E' da notare il fatto che questi manager non permettano di togliere i parametri aggiunti, dato che per come
è ideato il programma è più comodo andare a copiare un manager vuoto e aggiungervi parametri.

- **GuardianAPIManager:** è la classe che implementa l'interfaccia APIManager e contiene tutte le informazioni
per la chiamata dell'API del TheGuardian. Utilizza due classi ausiliarie, GuardianAPIInfo e GuardianAPIParams, semplicemente perchè
le funzioni che avrebbe dovuto implementare sarebbero state troppe e troppo complesse. In questo modo demanda a GuardianAPIInfo la 
gestione delle informazioni dell'API, e a GuardianAPIParams la gestione dei parametri da passare all'API.
Come tutti gli APIManager possiede una funzione per copiare l'oggetto, in quanto la semplice assegnazione copierebbe il riferimento
allo stesso oggetto, compromettendo la logica dell'APIContainer.
Vengono utilizzati dei parametri di default per semplificare la gestione delle risposte da parte dei deserializzatori e per essere
sicuri che le risposte siano in formato JSON. Inoltre, se non vengono specificati parametri, possiede la logica per scaricare 1000
articoli, salvandoli in 5 file, con la query "nuclear power".
Per migliorare le sue performance, viene utilizzato il parallelismo, fornito mediante la classe ThreadPool e i Runnable CallAPIThread.

### "Download" da folder presente in locale
Questa fase (non definita download all'interno del programma), avviene solamente se l'utente specifica il path di un
folder presente in locale da cui partire per la serializzazione. Se questa opzione non è specificata, viene dato il controllo
alla parte di download da una API.

## DESERIALIZZAZIONE

## SERIALIZZAZIONE

## ANALISI (estrazione termini)
- **Handler:** come ogni sottosistema di questo programma, anche la parte di analisi è gestita
interamente da un [handler](design_patterns.md), il quale nasconde al main la logica dell'analisi e ne fornisce
una semplice modalità di chiamata.
Implementa al suo interno una funzione per leggere il file di stop words (ovvero termini che non verranno contati
durante l'analisi dei termini degli articoli) presente nel codice sorgente, o uno passato esternamente dall'utente (se 
non presente, verrà usato quello di default).
Se l'utente non vuole usare queste stop words, può specificare da riga di comando di analizzare tutti i termini.

- **Stampare i termini in un file:** per stampare i termini in un file è stata creata un'interfaccia OutPrinter,
che può essere implementata da varie classi che hanno la logica per stampare la lista di termini in file di diversi formati.
Attualmente è stato implementato solo un OutPrinter che stampa i termini in un file .txt, ma in futuro è molto
semplice modificare il programma per stampare i termini in file con diverse estensioni, come spiegato in [flessibilità](flessibilita.md).

- **Analisi:** la parte di analisi è svolta da diverse classi che implementano l'interfaccia Analyzer, seguendo
lo [strategy design pattern](design_patterns.md). Infatti, all'interno del file di properties della parte di analisi è
specificata la strategia da adottare, e quindi quale classe utilizzare per fare questa analisi.
La strategia da noi adottata, presente in MapSplitAnalyzer, utilizza il metodo split della classe String per scomporre il testo da
analizzare nei vari termini (un termine è composto solamente da lettere), dato che StringTokenizer è definita deprecata all'interno della documentazione di Java.
Questi termini vengono considerati uguali anche se differiscono per maiuscole e minuscole: le parole in lower case per fare l'analisi.
Viene poi utilizzato il parallelismo per ottenere il risultato in maniera più efficiente, oltre a una funzione specifica
che pone in ordine i vari termini all'interno della lista ritornata.
L'alternativa di utilizzare una Priority Queue è affrontata nella parte dei [test](test.md), ma risulta inefficiente
in quanto si dovrebbe scansionare 2 volte l'insieme contenente tutti i termini: la prima volta per metterli dentro alla coda
e la seconda per estrarre dalla coda prioritaria i termini più importanti.
Per il peso dei termini viene utilizzata la classe OrderedEntryStringInt, la quale definisce che un termine è maggiore
dell'altro se compare in più articoli o, se il numero di articoli in cui compaiono è lo stesso, viene prima
in ordine alfabetico.

- **Analysis properties:** è utilizzato un file di properties per la parte di analisi: questo possiede al suo interno
2 proprietà: l'Analyzer da utilizzare per l'analisi e l'OutPrinter per stampare su file il risultato.
Questo rispetta quanto detto in [flessibilità](flessibilita.md).

- **Article e UnitOfSearch:** per la parte di analisi viene definita l'interfaccia UnitOfSearch. 
Questa interfaccia ha una funzione per ottenere la parte di testo da analizzare, ed estende l'interfaccia vuota
Serializable utilizzata nelle parti di Serializzazione e Deserializzazione.
In questo modo, la parte di analisi può avere successo per ogni classe che implementi UnitOfSearch.
Article è la classe che implementa UnitOfSearch, e viene utilizzata dai deserializzatori e serializzatori
definiti in questo programma come oggetto dove salvare i vari articoli.
Come spiegato in [flessibilità](flessibilita.md), è quindi molto semplice andare a modificare la classe che
implementa UnitOfSearch andando a definire dei nuovi deserializzatori.

## Ulteriori informazioni:
- [design patterns utilizzati](design_patterns.md)
- [flessibilità](flessibilita.md)
- [test](test.md)
- [librerie utilizzate e motivazioni](librerie.md)
- [problematiche incontrate e trade-off](problemi.md)