package it.unipd.dei.dbdc.deserialization.interfaces;

public interface DeserializerWithFields extends Deserializer{
    String[] getFields();
    void setFields(String[] fields);
}
