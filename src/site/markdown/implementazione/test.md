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
  - infine vengono fatti i system tests, ovvero i test presenti in AppTest.
il singleton e parallelismo (guardianapiparams e mapslitanalyzer) e poi le altre e poi i system

la classe queryparam non ha bisogno di test


kongapicallertest -> 30 chiamate
callapithread -> 21 chiamate
guardianapimanager -> 72 chiamate