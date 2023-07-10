# Generazione dei javadocs
Per generare i javadocs, digitare da riga di comando:

    mvn javadoc:javadoc

Essi verranno prodotti all'interno del folder `target/site/apidocs`, e apribili con un qualsivoglia browser.
I javadoc sono stati prodotti anche per i test: per generarli, digitare da riga di comando:

    mvn javadoc:test-javadoc

Essi verranno prodotti all'interno del folder `target/site/testapidocs`, e apribili con un qualsivoglia browser.
Questi ultimi sono molto più semplici di quelli del programma in sè, in quanto sarebbe stato impossibile
descrivere tutti i test effettuati all'interno di ogni classe per ogni funzione.