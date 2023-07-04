package it.unipd.dei.dbdc.deserialization.interfaces;

public interface DeserializerWithFields extends Deserializer{
    String[] getFields();

    int numberOfFields();
    void setFields(String[] fields);
}
