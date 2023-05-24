package it.unipd.dei.dbdc;


// primo approccio, funziona perchè seguo dei nodi predefiniti. Non è generale -> vedere json1
public class App 
{   /*
    public static void main( String[] args )
    {
        List<Article> articles = new ArrayList<>();
        String path_folder = "./database/the_guardian";
        String path_serialized_file = "./database/fileSerializzato.json";
        deserialization_JSON_folder(path_folder,articles);
        serialization_JSON_file(path_serialized_file, articles);
    }

    // Deserializzazione di tutti i file nel folder che terminano in JSON
    public static void deserialization_JSON_folder(String path, List<Article> articles){

        File folder = new File(path);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    deserialization_JSON_file(file, articles);
                }
            }
        }

    }

    // Deserialization: da file a oggetto (li mettiamo nella lista di article)
    public static void deserialization_JSON_file(File file, List<Article> articles){

        try {

            // Since the creation of an ObjectMapper object is expensive, it's recommended that we reuse the same one for multiple operations.
            // TODO: spostare l'ObjectMapper fuori da qui
            ObjectMapper objectMapper = new ObjectMapper();

            // Leggo il JSON come un tree
            JsonNode rootNode = objectMapper.readTree(file);

            // Prendo il nodo response
            JsonNode responseNode = rootNode.path("response");

            JsonNode ResponseArray = responseNode.path("results");

            // Per ogni articolo:
            for (JsonNode root : ResponseArray) {

                // Cerco fields
                JsonNode fieldsNode = root.path("fields");

                if (!fieldsNode.isMissingNode()) {        // if "fields" node exists
                    Article article = new Article(fieldsNode.path("headline").asText(), fieldsNode.path("bodyText").asText());
                    articles.add(article);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Serializzazione a partire dalla lista di Article
    public static void serialization_JSON_file(String path, List<Article> articles){

        try{

            // Creazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

            // Serializzazione della lista di articoli in un file JSON
            objectMapper.writeValue(new File(path), articles);

            // Serializzazione della lista di articoli in una stringa JSON
            String stringaJson = objectMapper.writeValueAsString(articles);
            System.out.println(stringaJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */
}









