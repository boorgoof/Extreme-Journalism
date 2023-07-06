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

Nel diagramma delle classi tutte le
associazioni hanno un nome e cardinalità? Le classi hanno gli
attributi necessari?