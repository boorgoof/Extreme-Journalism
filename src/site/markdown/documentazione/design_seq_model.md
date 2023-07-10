# Design Sequence Diagrams
Sono qui presenti i diagrammi dinamici del programma, ovvero i diagrammi di sequenza.
Vengono rappresentati i principali scenari e il relativo comportamento del software.

## System sequence diagrams
Il seguente diagramma di sequenza mostra le varie interazioni tra la classe principale 
del programma, l'utente e i vari sottosistemi del programma.
Sono presenti le varie alternative (non sono comprese le varie alternative
con le altre opzioni specificabili) che l'utente può selezionare da riga di comando:
inoltre, sono trattati come due casi diversi il download da una API e la serializzazione
da un folder di file già scaricati.

<img src="../images/Sequenza_tot.jpg"/>

## Internal sequence diagrams
#### Download diagram
Il seguente diagramma di sequenza mostra le varie interazioni tra la classe principale
del programma, l'utente e il sottosistema di download da una API.
Sono presenti due alternative:
- download da una API mediante un file di properties specificato e passato da riga di comando
- download da una API mediante interazione diretta tra il sottosistema di download e l'utente

Il file out_p è il file di properties esterno per download.properties.
Vengono utilizzate interfacce per questione di flessibilità, in quanto le varie classi che implementano le varie API possono cambiare.

<img src="../images/Sequenza_down.jpg"/>

#### Deserialization diagram
Il seguente diagramma di sequenza mostra le varie interazioni tra la classe principale
del programma, l'utente e il sottosistema di deserializzazione.
Sono presenti due funzioni, entrambe utilizzate nel programma, anche se in momenti diversi:
- deserializzazione di un folder (utilizzato nella fase di download da parte di una sorgente locale, cioè a 
  partire da file già presenti in locale)
- deserializzazione di un file (utilizzato nella fase di analisi, per la deserializzazione del file in formato
  comune)

Il file out_p è il file di properties esterno per deserializers.properties.
Vengono utilizzate interfacce per questione di flessibilità, in quanto le varie classi che implementano i deserializzatori possono cambiare.

<img src="../images/Sequenza_deser.jpg"/>

#### Serialization diagram
Il seguente diagramma di sequenza mostra le varie interazioni tra la classe principale
del programma, l'utente e il sottosistema di serializzazione, che avviene nel momento di
serializzazione degli articoli prelevati dalle diverse sorgenti per creare un file in un
formato comune.

Il file out_p è il file di properties esterno per serializers.properties.
Vengono utilizzate interfacce per questione di flessibilità, in quanto le varie classi che implementano i serializzatori possono cambiare.

<img src="../images/Sequenza_ser.jpg"/>

#### Analysis diagram
Il seguente diagramma di sequenza mostra le varie interazioni tra la classe principale
del programma, l'utente e il sottosistema di analisi, che avviene nel momento di
estrazione dei termini più importanti dagli articoli contenuti nel file in formato comune.

Il file out_p è il file di properties esterno per analyze.properties.
Vengono utilizzate interfacce per questione di flessibilità, in quanto le varie classi che implementano le varie strategie possono cambiare.

<img src="../images/Sequenza_an.jpg"/>