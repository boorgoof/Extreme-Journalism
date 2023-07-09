# COME E' STATA IMPLEMENTATA LA FLESSIBILITA' RICHIESTA
Principio fondante del programma è la flessibilità: si è cercato di scrivere tutto il codice
in modo che questo possa essere modificato in maniera molto facile: la parte principale
è sempre la stessa, lavora molto con interfacce per avere dei punti di Protected Variation, e ci
sono molti punti di Indirection per avere un Low Coupling e High Coesion.

## FLESSIBILITA' VERSO NUOVE SORGENTI
Questa flessibilità si basa in parte sui file di properties e in parte sull'utilizzo di interfacce:
all'interno di queste properties, è possibile definire delle nuove API che si possono chiamare, indicandone il nome
e la classe (che implementa APIManager) che ne gestisce la logica del passaggio di parametri all'API.
Dato che questi file di properties sono interni al programma, è possibile andare a passare dall'esterno un
diverso file di properties.
Per quando riguarda le sorgenti che sono scaricate dall'utente in maniera separata e i cui file vengono posti
all'interno di un folder, la flessibilità consiste nel file di deserializers.properties: qui sono indicati
tutti i deserializzatori che possono essere utilizzati dal programma, oltre al formato per cui devono essere utilizzati.
In questo modo è molto semplice andare ad aggiungere un nuovo deserializzatore (che implementi l'interfaccia Deserializer) per un file di formato che non
è presente all'interno dei deserializzatori forniti, garantendo flessibilità anche nei confronti di queste sorgenti.

## FLESSIBILITA' VERSO NUOVE STRUTTURE PER MEMORIZZARE ED AVERE ACCESSO AI TERMINI PIU' IMPORTANTI.
Questa flessibilità è presente nella parte di analisi, e più precisamente nel file analyze.properties:
al suo interno sono indicate le 2 classi principali della parte di analisi, ovvero quella che data una lista
di UnitOfSearch ne estrae i termini di maggiore importanza (che implementa l'interfaccia Analyzer), e quella che
stampa la lista di termini estratti in un file di output (che implementa OutPrinter). 
E' quindi molto semplice andare a modificare questo file, aggiungendo le dovute classi, per andare a ottenere una diversa
struttura di termini e anche un diverso modo di andare a mostrarli all'utente (il file di output potrebbe cambiare, o si
potrebbe andare a salvare il file in un database esterno).
Dato che questi file di properties sono interni al programma, è possibile andare a passare dall'esterno un
diverso file di properties.

## FLESSIBILITA' VERSO NUOVE MODALITA' DI MEMORIZZAZIONE ED ACCESSO AGLI ARTICOLI
Questa flessibilità è presente nei file di properties di serializzatori e deserializzatori, unita al file general.properties: nei primi 2 file sono presenti
delle classi che implementano rispettivamente Serializer e Deserializer e sono in grado di serializzare al formato specificato
o deserializzare dal formato specificato.
La memorizzazione attuale degli articoli avviene al formato comune XML, per cui sono stati creati deserializzatori e serializzatori
per questo formato, ma modificando il file general.properties si può andare a definire un diverso formato comune, come il JSON,
e verranno quindi utilizzati serializzatori e deserializzatori di questo formato per andare a creare il file dove
vengono memorizzati gli articoli e andare poi a leggerlo.
La modifica è minima, basta modificare una proprietà del file general.properties e implementare e dichiarare negli altri file
di properties i serializzatori e deserializzatori verso questo nuovo formato comune.
L'unica cosa importante è che questi Deserializer e Serializer vadano ad utilizzare oggetti che implementino
UnitOfSearch, in quanto solo questi oggetti sono poi analizzabili dalla parte di analisi.
Dato che questi file di properties sono interni al programma, è possibile andare a passare dall'esterno un
diverso file di properties.

## FLESSIBILITA' AGGIUNTIVE
### Flessibilità verso la libreria con cui effettuare le chiamate
La libreria attualmente utilizzata è [Kong Unirest](librerie.html), ma all'interno del file download.properties è presente
una proprietà che indica quale classe viene utilizzata per effettuare le chiamate all'API (ognuna di queste deve implementare l'interfaccia APICaller, e non è necessaria nessun altra modifica del programma).
Dato che questi file di properties sono interni al programma, è possibile andare a passare dall'esterno un
diverso file di properties.

### Flessibilità in serializzatori e deserializzatori
I deserializers implementati utilizzano Article, ma basta creare altri deserializzatori o serializzatori
per i formati già presenti, come XML, e andare a modificare il corrispondente file di properties.
Dato che questi file di properties sono interni al programma, è possibile andare a passare dall'esterno un
diverso file di properties.

### Flessibilità per il numero totale di termini da estrarre
Il numero di termini da estrarre, definito nelle general.properties, è 50.
Questo è modificabile sia da riga di comando, fornendo l'opzione -n, sia tramite il passaggio di nuove general.properties,
in modo da garantire che l'utente possa modificarlo in maniera comoda.

### Flessibilità per le stop words
Le stop words vengono di default utilizzate dal sistema, ma possono essere disabilitate da riga di comando.
Inoltre può essere passato un diverso file di stop words, ad esempio se si vuole scaricare degli articoli in lingua non inglese
(le default stop words sono in inglese).

### Flessibilità per l'aggiunta di opzioni da riga di comando
La classe CommandLineInterpreter è molto flessibile, in quanto aggiungere una nuova opzione consiste nell'aggiungerla in una delle 3 parti possibili
(download, analisi e generali) e aggiungere una nuova funzione per ottenere il valore passatole.

### Flessibilità per la modifica del luogo dove salvare i file
La classe PathManager ha tutta la logica del luogo dove vengono salvati i file: modificare essa comporta una
modifica dell'intero programma in maniera molto veloce, basta modificare un field della classe stessa.