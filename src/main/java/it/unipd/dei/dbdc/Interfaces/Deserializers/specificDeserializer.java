package it.unipd.dei.dbdc.Interfaces.Deserializers;

public interface specificDeserializer<T> extends Deserializer<T>{
    String[] getFields();
    void setFields(String[] fields);
}
