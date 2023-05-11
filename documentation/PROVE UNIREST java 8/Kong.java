package org.example;

// import kong.unirest.*;

public class Kong {
/*
    public static void main( String[] args )
    {
        // 1. Setto l'objectmapper che serve per serializzare e deserializzare
        //ObjectMapper mapper = new MyObjectMapper();

        // Attenzione: il mapper che metto dentro non puo' essere di jackson, ma deve essere del tipo
        // kong.unirest.ObjectMapper
        // Quindi questo non funziona:
        // Unirest.setObjectMapper(new com.fasterxml.jackson.databind.ObjectMapper());

        // Per fare una cosa equivalente posso creare un objectmapper di jackson e poi ritornare i valori da lui restituiti
        // Unirest.setObjectMapper(mapper);

        // Un ObjectMapper gia' presente in kong e' il JsonObjectMapper()
        ObjectMapper ob = new JsonObjectMapper();

        // Proviamo a leggere un JSON
        String prova = "{ \"status\" : \"ok\", \"pageSize\" : \"200\", \"currentPage\" : \"2\" }";
        Response r = ob.readValue(prova, Response.class);
        System.out.println(r);
    }
 */
}

// Classe per testare l'ObjectMapper di Kong
class Response
{
    private String status;
    private int pageSize;
    private int currentPage;

    public String toString()
    {
        return "status: "+status+"\npageSize: "+pageSize+"\ncurrentPage"+currentPage;
    }
}

/*
// Questa e' la classe utilizzabile per com.mashape.unirest, che sfrutta l'ObjectMapper di Jackson.
Il funzionamento e' di fatto lo stesso di kong.Unirest, e un esempio e' in clien-theGuardianApi

class MyObjectMapper implements ObjectMapper
{
    // Questo e' l'objectmapper di jackson
    private final com.fasterxml.jackson.databind.ObjectMapper map = new com.fasterxml.jackson.databind.ObjectMapper();

    // Trasforma un JSON in un oggetto della classe valueType
    public <T> T readValue(String value, Class<T> valueType)
    {
        try {
            return map.readValue(value, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Trasforma un oggetto in JSON, che viene ritornato come String
    public String writeValue(Object value)
    {
        try {
            return map.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
*/