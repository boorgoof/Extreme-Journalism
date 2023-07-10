# SPIEGAZIONE DELLA STRUTTURA DEI TEST
Ogni funzione pubblica di ogni classe è stata testata con valori di ogni genere (essenzialmente si tratta di test
basati su diverse partizioni, alcune che avranno successo e altre no), per vedere quali output e quali eccezioni lanciasse, andando anche a fare degli stress test (ovvero test con un gran numero di parametri).

Alcune spiegazioni su come sono strutturati e alcune eccezioni:
- Viene fornito un ordine ai test: 
  - prima vengono testate le classi che utilizzano il [Singleton design pattern](design_patterns.md),
    dato che una volta inizializzate con dei file di properties queste classi restituiranno sempre la stessa istanza
    (a queste classi viene quindi dato ordine 1, in modo che siano le prime ad essere testate)
  - dopodiché vengono testate tutte le altre classi: l'ordine di test di queste ultime è indifferente,
    quindi hanno tutte lo stesso ordine (7, in vista di una flessibilità futura in cui si vadano ad aggiungere
    classi che dovrebbero essere testate tra le prime e queste).
    All'interno di questi test sono presenti anche i test dei componenti, ovvero dei 4 sottosistemi di cui è composto il programma,
    ognuno di essi testato nell'Handler di riferimento.
  - infine vengono fatti i system tests, ovvero i test presenti in AppTest (ordine 14, sempre per poter mettere altri test nel mezzo).

C'è una eccezione: la classe QueryParam non ha bisogno di test, dato che è semplicemente uno pseudonimo per una
classe della libreria standard di Java (Map.Entry<String, String>).

La parte di test effettua molte chiamate alla TheGuardianAPI per testare le varie risposte ottenute.
Questo utilizza un certo numero di chiamate con la key specificata nei vari files elencati [qui](../istruzioni/installazione.html) (se questa key è assente,
i test falliranno):
- KongAPICallerTest -> 30 chiamate
- CallAPIThread -> 21 chiamate
- GuardianAPIManager -> 72 chiamate (22 senza lo stress test)
- AppTest -> 5 chiamate

