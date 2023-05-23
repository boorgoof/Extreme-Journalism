# COME UTILIZZARE LE PROPRIETA' DI UN SISTEMA
Most Java application need to use properties at some point, generally to store simple parameters as key-value pairs, outside of compiled code.
And so the language has first class support for properties – the java.util.Properties – a utility class designed for handling this type of configuration files.

1. Use .properties as suffix of the file of properties (anche se non è necessario)
2. How to make a properties file:
   Each line in a .properties file normally stores a single property. 
   Several formats are possible for each line, including key=value, key = value, key:value, and key value. 
   Single-quotes or double-quotes are considered part of the string
   Comment lines in .properties files are denoted by the number sign (#) or the exclamation mark (!) as the first non blank character, in which all remaining text on that line is ignored. 
   The backwards slash is used to escape a character. If you want to put the value on two lines,
   use a single \ character.
3. ATTENZIONE: la risorsa deve essere nel package resources dentro a main
3. How to load them in Properties objects:
```java
String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
String appConfigPath = rootPath + "app.properties";

Properties appProps = new Properties();
appProps.load(new FileInputStream(appConfigPath));

String appVersion = appProps.getProperty("version");
```
4. Another way is to load from an XML file of properties:
```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
    <comment>xml example</comment>
    <entry key="fileIcon">icon1.jpg</entry>
    <entry key="imageIcon">icon2.jpg</entry>
    <entry key="videoIcon">icon3.jpg</entry>
</properties>
```
To load it in an object :
```java
iconProps.loadFromXML(new FileInputStream(iconConfigPath));
```
5. To get properties we can  use getProperty(String key) and getProperty(String key, String defaultValue) to get value by its key.
   If the key-value pair exists, the two methods will both return the corresponding value. 
   But if there is no such key-value pair, the former will return null, and the latter will return defaultValue instead.
6. A Properties object can contain another Properties object as its default property list.
   To put it in the other object, use:
```java
Properties defaultProps = new Properties();
Properties appProps = new Properties(defaultProps);
```
7. With these objects we can store properties, remove properties or store tem in a file:
```java
String newAppConfigPropertiesFile = rootPath + "newApp.properties";
appProps.store(new FileWriter(newAppConfigPropertiesFile), "store to properties file");
```