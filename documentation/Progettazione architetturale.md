# ARCHITETTURA DEL SISTEMA
L'architettura è del tipo pipe and filter: 
L’elaborazione dei dati del sistema è organizzata in modo che ciascun
componente di elaborazione (filtro) sia discreto e svolga un particolare
tipo di trasformazione dei dati. I dati fluiscono, come in un
tubo (pipe), da un componente all’altro per essere elaborati.

Bisogna vedere se bisogna fare tutti i diagrammi UML per tutti i modelli (contestuali, di interazione, strutturali, comportamentali).
Guarda slide/libro per farli bene.

## SUDDIVISIONE DI BASE (vedere anche diagramma UML Schema_progetto)

### Richeste HTTP al theGuardian
Si possono fare in vari modi:
- In java 11 si possono fare nativamente
- In java 8 (richiesta dai requisiti) si possono fare con varie librerie (vedere alternative)

L'output è un insieme di file JSON (5 come minimo per 1000 articoli, ma si possono aumentare)

### Deserializzatore e serializzatore in formato comune
Dopo averli scaricati dal theGuardian o presi da dei file in database, vengono convertiti in un file XML unico con questa struttura:
```xml
<article>
  <id></id>
  <title></title>
  <body></body>
</article>
```
Il prof ha detto di mettere dentro anche altre cose comuni, come:
- url
- data
- sezione
- testata

### Deserializzatore da formato comune e ricerca termini

Per la ricerca di termini:
- Ogni articolo viene messo in una mappa (la parola è la key), che viene poi:
  - messa dentro ad una priority queue per poi estrarre i primi termini (meno efficiente)
  - scannerizzata tutta per prendere i primi termini direttamente
- L'alternativa è mettere ogni articolo in una priority queue (il numero di occorrenze è la key),
  ma bisogna vedersela bene perchè non so come si faccia ad estrarre la chiave di quella introdotta.
  In caso si puo' creare una propria AdaptablePriorityQueue. 
