Nel diagramma delle classi tutte le
associazioni hanno un nome e cardinalità? Le classi hanno gli
attributi necessari?

# Domain Model

Molte delle classi che sono utilities classes (come ThreadPool, PropertiesTools, ma anche quelle utilizzate
dall'Analyzer e dal APIManager implementati, oltre che quelle di Properties) non sono indicate, in quanto
frutto di una successiva migliore suddivisione del codice (non rappresentano oggetti reali).
Le interfacce sono messe per visualizzare fin da subito l'importanza della flessibilità all'interno del progetto.

<img src="../images/Domain.jpg"/>