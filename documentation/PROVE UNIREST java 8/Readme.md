# HOW TO
https://www.baeldung.com/jackson-object-mapper-tutorial
- We're passing headers and parameters with the header() and fields() APIs.
- And the request gets invoked on the asJson() method call; we also have other options here, such as asBinary(), asString() and asObject().
- To pass multiple headers or fields, we can create a map and pass them to .headers(Map<String, Object> headers) and .fields(Map<String, String> fields) respectively:
- To pass data as a query String, we'll use the queryString() method:
  .queryString("apiKey", "123")
- Unirest.get("http://www.mocky.io/v2/5a9ce7663100006800ab515d")
- Once we get the response, let check the status code and status message:
  jsonResponse.getStatus()
  jsonResponse.getHeaders();
  jsonResponse.getBody();
  jsonResponse.getRawBody();
- Unirest.shutdown();
# Usare Object Mapper
- In order to use the asObject() or body() in the request, we need to define our object mapper. For simplicity, we'll use the Jackson object mapper. 
In pratica mappiamo la risposta in un oggetto. Now let's configure our mapper:
```java
Unirest.setObjectMapper(new ObjectMapper() 
{
  com.fasterxml.jackson.databind.ObjectMapper mapper
  = new com.fasterxml.jackson.databind.ObjectMapper();

  public String writeValue(Object value) 
  {
  return mapper.writeValueAsString(value);
  }

  public <T> T readValue(String value, Class<T> valueType) 
  {
  return mapper.readValue(value, valueType);
  }

  });
```
Note that setObjectMapper() should only be called once, for setting the mapper; once the mapper instance is set, it will be used for all request and responses.

- serializing a Java object into JSON using the writeValue method
```java
ObjectMapper objectMapper = new ObjectMapper();
Car car = new Car("yellow", "renault");
objectMapper.writeValue(new File("target/car.json"), car);
```
The methods writeValueAsString and writeValueAsBytes of ObjectMapper class generate a JSON from a Java object and return the generated JSON as a string or as a byte array

- converting a JSON String to a Java object
```java
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = objectMapper.readValue(json, Car.class);
```
The readValue() function also accepts other forms of input, such as a file containing JSON string (.json)

- Another essential feature of the ObjectMapper class is the ability to register a custom serializer and deserializer.
Custom serializers and deserializers are very useful in situations where the input or the output JSON response is different in structure than the Java class into which it must be serialized or deserialized.
Questo viene fatto creando un SimpleModule, al quale aggiungeremo un serializer, e poi aggiungeremo il modulo all'objectmapper.
A questo punto, quando andremo a fare readValue o writeValue su un oggetto di quel tipo, si attivera' quel modulo, serializzando/deserializzando solo quello che vogliamo
```java
public class CustomCarSerializer extends StdSerializer<Car> {

    public CustomCarSerializer() {
        this(null);
    }

    public CustomCarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(
      Car car, JsonGenerator jsonGenerator, SerializerProvider serializer) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("car_brand", car.getType());
        jsonGenerator.writeEndObject();
    }
}
```

This custom serializer can be invoked like this:
```java
ObjectMapper mapper = new ObjectMapper();
SimpleModule module =
new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
module.addSerializer(Car.class, new CustomCarSerializer());
mapper.registerModule(module);
Car car = new Car("yellow", "renault");
String carJson = mapper.writeValueAsString(car);
```

Custom deserializer
```java
public class CustomCarDeserializer extends StdDeserializer<Car> {

    public CustomCarDeserializer() {
        this(null);
    }

    public CustomCarDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Car deserialize(JsonParser parser, DeserializationContext deserializer) {
        Car car = new Car();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        
        // try catch block
        JsonNode colorNode = node.get("color");
        String color = colorNode.asText();
        car.setColor(color);
        return car;
    }
}
```

invoked like this
```java
String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
ObjectMapper mapper = new ObjectMapper();
SimpleModule module =
new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
module.addDeserializer(Car.class, new CustomCarDeserializer());
mapper.registerModule(module);
Car car = mapper.readValue(json, Car.class);
```