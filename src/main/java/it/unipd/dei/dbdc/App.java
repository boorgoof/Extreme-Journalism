package it.unipd.dei.dbdc;


import it.unipd.dei.dbdc.analyze.AnalyzeProperties;
import it.unipd.dei.dbdc.deserialization.DeserializationProperties;
import it.unipd.dei.dbdc.download.DownloadProperties;
import it.unipd.dei.dbdc.resources.PathManager;
import it.unipd.dei.dbdc.analyze.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analyze.AnalyzerHandler;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;
import it.unipd.dei.dbdc.download.DownloadHandler;
import it.unipd.dei.dbdc.serializers.SerializationHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    //TODO: stampa cose più significative, e vedi bene dove vanno le varie eccezioni

    public static void main(String[] args) {

        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (IllegalStateException e)
        {
            System.err.println("The program has been terminated because there was no action to perform specified.");
            return;
        }

        if (interpreter.help())
        {
            return;
        }

        TotalProperties totalProperties;
        try {
            totalProperties = new TotalProperties(interpreter.obtainTotProps());
        }
        catch (IOException e)
        {
            System.err.println("The program has been terminated because the file "+TotalProperties.default_properties+" was not found: "+e.getMessage());
            return;
        }

        // Folder to serialize.
        String folderPath = null;

        // FIRST PHASE: download
        if (interpreter.downloadPhase()) {
            System.out.println("Entering the download part...");
            try {
                //Obtains the properties from the command line, if specified, and calls the handler.
                folderPath = DownloadHandler.download(interpreter.obtainDownProps(), interpreter.obtainAPIProps());
            }
            catch (IOException e)
            {
                System.err.println("The program has been terminated because the file "+ DownloadProperties.default_properties+" was not found: "+e.getMessage());
                return;
            }

            System.out.println("Exiting the download part...\n");
        }

        // SECOND PHASE: it always happens, as the serialization is always necessary.

        // A. DESERIALIZATION from the given format to a List of Objects

        //Obtains the properties from the command line, if specified
        String path_cli = interpreter.obtainPathOption();

        if (path_cli == null && folderPath == null) {
            System.err.println("Error: there is no file to serialize.");
            return;
        } else if (path_cli != null) {
            folderPath = path_cli;
        }

        System.out.println("\nEntering the deserialization of "+folderPath+"...");

        //Initializes the handler.
        DeserializationHandler deserializersHandler;
        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            deserializersHandler = new DeserializationHandler(interpreter.obtainDeserProps());
        }
        catch (IOException e)
        {
            System.err.println("The program has been terminated because the file "+ DeserializationProperties.default_properties+" was not found: "+e.getMessage());
            return;
        }

        //Tries to deserialize the specified folder.
        List<UnitOfSearch> articles;
        try{
             articles = deserializersHandler.deserializeFolder(folderPath);
        } catch (IOException e){
            System.err.println(e.getMessage()); //TODO: eccezioni
            return;
        }

        System.out.println("Exiting the deserialization part...\n");


        // B. SERIALIZATION to the List of Objects to the common format

        System.out.println("\nEntering the serialization part...");

        //Path of the serialized file
        String filePath;
        try {
            filePath = PathManager.getSerializedFile(totalProperties.getCommonFormat());
        }
        catch (IOException e)
        {
            System.err.println("Error: it was not possible to create the serialized file.");
            return;
        }

        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            SerializationHandler serializersHandler = new SerializationHandler(interpreter.obtainSerProps());
            serializersHandler.serializeObjects(articles, totalProperties.getCommonFormat(), filePath);
        } catch (IOException e) {
            System.err.println("Error during the serialization: "+e.getMessage());
            return;
        }
        System.out.println("Exiting the serialization part. You can find the serialized file in "+filePath+"...\n");

        // THIRD PHASE: analyze
        if (interpreter.analyzePhase())
        {
            // A. DESERIALIZATION from common_format to a list of Objects
            System.out.println("\nEntering the deserialization of "+filePath+"...");

            try {
                //TODO: AL MOMENTO USA LA SECONDA VERSIONE DI DESERIALIZER. DA CAMBIARE?
                File commonFormatFile = new File(filePath);
                articles = deserializersHandler.deserializeFile(commonFormatFile);
            }
            catch (IOException e) {
                System.err.println("Error: "+e.getMessage());
                return;
            }
            System.out.println("Exiting the deserialization part...\n");

            // B. ANALYZE the Objects to obtain the most important words

            System.out.println("\nEntering the analyze part...");

            //Obtains the properties from the command line, if specified.
            int count = interpreter.obtainNumberOption();
            if (count == -1)
            {
                count = totalProperties.getWordsCount();
            }

            String out_file;
            try {
                //Obtains the properties from the command line, if specified, and calls the handler.
                out_file = AnalyzerHandler.analyze(interpreter.obtainAnalyzeProps(), articles, count, interpreter.obtainStopWords());
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("The program has been terminated: "+e.getMessage());
                return;
            }
            System.out.println("Exiting the analyze part. You can find the resulting file in"+out_file+"...\n");

        }
        System.out.println("Everything went correctly.\nThank you for choosing our application.");
    }

}









