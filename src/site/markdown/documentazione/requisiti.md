# DOCUMENTO DEI REQUISITI
Requisiti d'utente: sono ad alto livello.
Requisiti di sistema: più specifici.

Requisiti funzionali: definizione dei servizi che il sistema deve fornire, come reagire a particolari input, 
come reagire in particolari situazioni.
Descrivono ciò che il sistema deve fare.
devono specificare dettagliatamente
- funzioni
- input
- output
- eccezioni

Requisiti non funzionali: vincoli sulle funzioni/servizi offerti dal sistema,
vincoli temporali sul processo di sviluppo, vincoli legati a normative

## Requisiti di sistema
* Linguaggio: Java 8 (opzionale, se usata una versione diversa specificarne il motivo)
* Commentato in modo da poter utilizzare javadoc
* Unit testing eseguito con JUnit
* Documentazione prodotta come gdoc o tramite il Maven site plugin

[Casi d'uso](casi_uso.md)

## Requisiti d'utente
Progettare ed implementare un sistema software in grado di
scaricare (download) articoli da testate giornalistiche online resi
disponibili da diverse sorgenti e di estrarre e visualizzare i termini
più “importanti” nell’insieme degli articoli scaricati.

### Sorgenti
Un file o un insieme di file (ad esempio in formato CSV o JSON) contenente articoli o un servizio da cui scaricarli (ad esempio le API del The Guardian).
In modo che sia possibile aggiungere una nuova sorgente senza la necessità di apportare un numero elevato di modifiche.
1000 per da scaricare mediante le API del The Guardian, 1000 dal CSV (sono già 1000 nel file). La query utilizzata per scaricare gli articoli è "nuclear power".
- File CSV la cui prima riga è l'header, con la lista di attributi/campi
- [TheGuardianAPI](https://open-platform.theguardian.com/documentation/)
- Il sistema deve poter supportare nuove sorgenti
- Dopo la fase di download, deve essere effettuata la persistenza su file
  degli articoli usando lo stesso formato per tutti gli articoli di tutte le
  sorgenti. Questa fase può essere svolta utilizzando una libreria per serializzare 
  e deserializzare gli articoli.
- Il sistema deve poter supportare nuove modalità di memorizzazione ed
  accesso agli articoli.

### Estrazione termini (analisi)
- Un termine è una parola che compare nel titolo + corpo dell'articolo, ed ha un peso pari
  al numero di articoli in cui appare. Se ci sono pareggi, viene usato l'ordine alfabetico.
- Estrarre i 50 termini con maggior peso e memorizzarli in un file di
  testo (txt), dove ciascuna riga deve essere nel formato:
  “termine peso”
- Per estrarre i termini ed il loro peso (numero di documenti in cui appare),
  è necessario partire dai file in cui gli articoli sono memorizzati
- Il sistema deve poter supportare nuove strutture per memorizzare ed
  avere accesso ai termini più importanti
- L’utente deve poter specificare se eseguire solo il download, solo
  l’estrazione dei termini a partire dai file in cui sono stati memorizzati gli
  articoli, o entrambe le azioni in sequenza.
