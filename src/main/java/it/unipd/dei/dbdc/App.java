package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.deserialization.DeserializationProperties;
import it.unipd.dei.dbdc.download.DownloadProperties;
import it.unipd.dei.dbdc.tools.CommandLineInterpreter;
import it.unipd.dei.dbdc.tools.PathManager;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.AnalyzerHandler;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;
import it.unipd.dei.dbdc.download.DownloadHandler;
import it.unipd.dei.dbdc.serializers.SerializationHandler;
import it.unipd.dei.dbdc.tools.GeneralProperties;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The main class of the application: it invokes the {@link CommandLineInterpreter} to
 * check the actions to perform, and gives control to the different Handlers, linking their results.
 * The use of the Handlers is an example of the Facade design pattern.
 *
 */
public class App
{
    public static void main(String[] args) {

        // Parses the commands given
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("The program has been terminated because there was no action to perform specified.");
            return;
        }

        // After this point, it is certain that cmd in the CommandLineInterpreter will not throw NullPointerException
        if (interpreter.help())
        {
            return;
        }

        //Parses the general properties of the application
        GeneralProperties totalProperties;
        try {
            totalProperties = new GeneralProperties(interpreter.obtainGenProps());
        }
        catch (IOException e)
        {
            System.err.println("The program has been terminated because the file "+ GeneralProperties.default_properties+" was not found, or the properties passed by the user were not valid: "+e.getMessage());
            return;
        }

        String folderPath = null;

        // 1: download
        if (interpreter.downloadPhase()) {
            System.out.println("Entering the download part...");
            try {
                //Obtains the properties from the command line, if specified, and calls the handler.
                folderPath = DownloadHandler.download(interpreter.obtainDownProps(), interpreter.obtainAPIProps());
            }
            catch (IOException e)
            {
                System.err.println("The program has been terminated because the file "+ DownloadProperties.default_properties+" was not found, or the properties passed by the user were not valid: "+e.getMessage());
                return;
            }
            System.out.println("Exiting the download part...\n");
        }

        // 2: it always happens, as the serialization is always necessary.

        // 2.1 DESERIALIZATION from the given format to a List of Objects

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
            //todo: passargli anche interpreter.obtainSetFields()
            deserializersHandler = new DeserializationHandler(interpreter.obtainDeserProps());
        }
        catch (IOException e)
        {
            System.err.println("The program has been terminated because the file "+ DeserializationProperties.default_properties+" was not found, or the properties passed by the user were not valid: "+e.getMessage());
            return;
        }

        List<UnitOfSearch> articles = deserialize(deserializersHandler, folderPath);
        if (articles == null)
        {
            return;
        }
        System.out.println("Exiting the deserialization part...\n");


        // 2.2. SERIALIZATION to the List of Objects to the common format

        System.out.println("\nEntering the serialization part...");
        File serializedFile = serialize(articles, totalProperties.getCommonFormat(), interpreter);
        if (serializedFile == null)
        {
            return;
        }
        System.out.println("Exiting the serialization part. You can find the serialized file in "+serializedFile.getPath()+"...\n");

        // 3: analysis
        if (interpreter.analyzePhase())
        {
            analysis(serializedFile, deserializersHandler, interpreter, totalProperties.getWordsCount());
        }
        System.out.println("Everything went correctly.\nThank you for choosing our application, we hope to see you soon.");
    }

    private static List<UnitOfSearch> deserialize(DeserializationHandler deserializersHandler, String folderPath)
    {
        //Tries to deserialize the specified folder.
        List<UnitOfSearch> articles;
        try{
            articles = deserializersHandler.deserializeFolder(folderPath);
        } catch (IOException e){
            System.err.println("The program has been terminated because there was an error in the deserialization: "+e.getMessage());
            return null;
        }
        return articles;
    }

    public static File serialize(List<UnitOfSearch> articles, String common_format, CommandLineInterpreter interpreter)
    {
        //Path of the serialized file
        File serializedFile;
        try {
            serializedFile = new File(PathManager.getSerializedFile(common_format));
        }
        catch (IOException e)
        {
            System.err.println("Error: it was not possible to create the serialized file.");
            return null;
        }

        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            SerializationHandler serializersHandler = new SerializationHandler(interpreter.obtainSerProps());
            serializersHandler.serializeObjects(articles, serializedFile);
        } catch (IOException e) {
            System.err.println("Error during the serialization: "+e.getMessage());
            return null;
        }
        return serializedFile;
    }

    public static int analysis(File serializedFile, DeserializationHandler deserializersHandler, CommandLineInterpreter interpreter, int properties_word_count)
    {
        // A. DESERIALIZATION from common_format to a list of Objects
        System.out.println("\nEntering the deserialization of "+serializedFile.getPath()+"...");

        List<UnitOfSearch> articles;
        try {
            articles = deserializersHandler.deserializeFile(serializedFile);
        }
        catch (IOException e) {
            System.err.println("Error during the deserialization of the common format: "+e.getMessage());
            return -1;
        }

        System.out.println("Exiting the deserialization part...\n");

        // B. ANALYZE the Objects to obtain the most important words

        System.out.println("\nEntering the analysis part...");

        //Obtains the properties from the command line, if specified.
        int count = interpreter.obtainNumberOption();
        if (count <= 0)
        {
            count = properties_word_count;
        }

        String out_file;
        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            out_file = AnalyzerHandler.analyze(interpreter.obtainAnalyzeProps(), articles, count, interpreter.obtainStopWords());
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("The program has been terminated for an error in the analysis: "+e.getMessage());
            return -1;
        }
        System.out.println("Exiting the analysis part. You can find the resulting file in"+out_file+"\n");
        return 0;
    }
}









