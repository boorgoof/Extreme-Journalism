# DOCUMENTO DEI REQUISITI

## Prefazione
Versione 1.0.
Il seguente documento è rivolto a utenti che vogliono utilizzare il programma e a
programmatori che lo vogliono comprendere per modificarlo.

## Introduzione
Il seguente sistema è un software in grado di scaricare articoli da testate giornalistiche 
online resi disponibili da diverse sorgenti e di estrarre e visualizzare i termini
con peso maggiore nell’insieme degli articoli scaricati.
L’utente può specificare se eseguire solo il download, solo l’estrazione dei termini a partire 
dai file in cui sono stati memorizzati gli articoli, o entrambe le azioni in sequenza.

Per ulteriori informazioni: [casi d'uso](casi_uso.html)

## Glossario
- **API** = Le Application Programming Interface (API) sono meccanismi che consentono a due componenti software
  di comunicare tra di loro usando un insieme di definizioni e protocolli. [Ulteriori informazioni](https://aws.amazon.com/it/what-is/api/)
- **Sorgente** = Un file o un insieme di file (ad esempio in formato CSV o JSON) contenente articoli o 
  un servizio da cui scaricarli (ad esempio le API del The Guardian).
- **Termine** = Un termine è una parola che compare nel titolo o nel corpo dell'articolo. Per parola si intende
  un qualsiasi vocabolo che contenga solamente lettere alfabetiche. Il conteggio delle parole viene fatto indifferentemente
  dal fatto che queste presentino lettere maiuscole o minuscole.
- **Peso di un termine** = Il peso di un termine è pari al numero di articoli in cui appare. 
  Se ci sono pareggi tra più termini, viene usato l'ordine alfabetico: quello con peso maggiore sarà quello
  che viene prima in ordine alfabetico.

## Definizione dei requisiti dell’utente
### Requisiti funzionali:
**Download:**
Dopo la fase di download, viene effettuata la persistenza su file degli articoli
usando un formato comune per tutti gli articoli di tutte le sorgenti.
Le due sorgenti per cui deve essere possibile fare il download sono:
- File CSV la cui prima riga è l'header, con la lista di attributi/campi
- TheGuardianAPI: [qui c'è la documentazione](https://open-platform.theguardian.com/documentation/).
  La query utilizzata per scaricare gli articoli è "nuclear power".

**Estrazione termini (analisi):**
Per estrarre i termini è necessario partire dai file in cui gli articoli sono memorizzati.
Se tale file non è presente, il programma termina.
Il sistema deve essere in grado di estrarre i 50 termini con maggior peso e memorizzarli in un file di
testo (txt), dove ciascuna riga deve essere nel formato:
“termine peso”

## Architettura del sistema
Per avere delle informazioni su come questo sia stato implementato: [implementazione](../implementazione/spiegazione.html)

## Requisiti del sistema
- Linguaggio: Java 8 (opzionale, se utilizzata una versione diversa specificarne il motivo)
- Commentato in modo da poter utilizzare javadoc
- Unit testing eseguito con JUnit
- Documentazione prodotta come gdoc o tramite il Maven site plugin

## Modelli del sistema
- [Casi d'uso](casi_uso.html)
- [Domain model](domain_model.html)
- [Sequence design model](design_seq_model.html)
- [Class design model](design_class_diagrams.html)

## Evoluzione del sistema
Il sistema deve poter supportare:

- nuove sorgenti (cioè che sia possibile aggiungere una nuova sorgente senza la necessità di apportare un numero elevato di modifiche).

- nuove strutture per memorizzare ed avere accesso ai termini più importanti.

- nuove modalità di memorizzazione ed accesso agli articoli.

Per ulteriori informazioni su come ciò sia stato implementato: [Flessibilità](../implementazione/flessibilita.html)