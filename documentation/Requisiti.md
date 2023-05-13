# ANALISI DEI REQUISITI
**CONSEGNA: 18/07/2023**

## Tecnici
* Linguaggio Java 8
* Test con JUnit
* Commentato con javadoc
* Documentazione con gdoc o Maven site plugin
* Deve essere eseguibile da riga di comando (magari con file bash)

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

Un termine è una parola che compare nel titolo + corpo dell'articolo, ed ha un peso pari
al numero di articoli in cui appare. Se ci sono pareggi, viene usato l'ordine alfabetico.

## Sorgenti
- File CSV o JSON già scaricati
- File del TheGuardianAPI (per vedere esempi usa Postman)
- ALTRE SORGENTI

## Database
- File in formato comune (XML)
- ALTRI DATABASE e ALTRI FORMATI COMUNI

## Rierca termini
Deve poter supportare nuove strutture per memorizzare ed avere accesso ai termini più importanti