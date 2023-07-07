# COME E' STATA IMPLEMENTATA LA FLESSIBILITA' RICHIESTA

## FLESSIBILITA' VERSO NUOVE SORGENTI
Tramite le properties download.properties si possono andare ad utilizzare diverse librerie per l'effettiva chiamata
dell'API (ognuna di queste deve implementare l'interfaccia APICaller), e anche diverse classi che gestiscono la logica
dei parametri passati all'API (ognuna di queste deve implementare l'interfaccia APIManager).

## FLESSIBILITA' VERSO NUOVE STRUTTURE PER MEMORIZZARE ED AVERE ACCESSO AI TERMINI PIU' IMPORTANTI.

## FLESSIBILITA' VERSO NUOVE MODALITA' DI MEMORIZZAZIONE ED ACCESSO AGLI ARTICOLI

# FLESSIBILITA' AGGIUNTIVE

## FLESSIBILITA' IN SERIALIZZATORI E DESERIALIZZATORI
I deserializers implementati utilizzano Article.
Si sarebbe potuto utilizzare la riflessione per avere deserializzatori più generali, ma solo
imponendo che questi Objects 