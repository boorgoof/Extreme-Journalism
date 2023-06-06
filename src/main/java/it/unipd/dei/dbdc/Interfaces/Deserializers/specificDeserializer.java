package it.unipd.dei.dbdc.Interfaces.Deserializers;

public interface specificDeserializer extends Deserializer{
    String[] getFields();
    void setFields(String[] fields);
}
