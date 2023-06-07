## APP
- CLI -> IllegalStateException (Catchata da App)
- PropertiesTools -> IOException (non trova il file)

## Download
- Handler -> IOException (propagata dal container)
- InteractiveSelectAPI -> IOException (dal container)
- APIContainer -> IOException (da downloadproperties). POTREBBE RITORNARE ALTRA ECCEZIONE SE CERCO NELLA MAPPA IL NOME SBAGLIATO
- DownloadProperties -> Lancia IOException
- APIProperties -> IOException da se o dal container
- GuardianAPIManager -> IllegalArgumentException o IOException
- GuardianAPIParams -> IllegalArgumentException
- CallAPIThread -> IllegalArgument o IOException
- KongAPICaller -> IOException

## Analyzer
- Handler -> IOException (dalle properties o dalla scrittura su file)
- AnalyzerArticleThread -> RuntimeException (dal semaforo)
- MapArraySplitAnalyzerParallel -> IllegalStateException (per parallelismo)

## Deserializers
- Handler -> IOException (properties o altro)
- DeserializationProperties -> IOException
- Container -> IOException
- Article -> IllegalArgument
- CSV -> IOException (perche' definita nell'interfaccia)
- JSON -> IOException
- XML -> IOException

## Serializers
- CSV, XML, JSON -> IOException (definita in interfaccia)
