# SPIEGAZIONE DELLA STRUTTURA DEI TEST
Ogni funzione pubblica di ogni classe è stata testata con valori di ogni genere, per vedere
quali output e quali eccezioni lanciavano, andando anche a volte a fare degli stress test.
Alcune spiegazioni su come sono strutturati e alcune eccezioni:
- Viene fornito un ordine ai test: prima 
testiamo in ordine per il singleton e parallelismo (guardianapiparams e mapslitanalyzer) e poi le altre e poi i system

la classe queryparam non ha bisogno di test


kongapicallertest -> 30 chiamate
callapithread -> 21 chiamate
guardianapimanager -> 72 chiamate