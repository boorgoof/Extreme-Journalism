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
import java.io.Serializable;
import java.util.ArrayList;
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

        //There are a few things that should be done before the start of the application:

        //1. Parse the commands given
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        } catch (IllegalArgumentException e) {
            System.err.println("The program has been terminated because there was no action to perform specified.");
            return;
        }

        // After this point, it is certain that cmd in the CommandLineInterpreter will not throw NullPointerException
        if (interpreter.help()) {
            return;
        }

        //2. Parse the general properties of the application
        GeneralProperties totalProperties;
        try {
            totalProperties = new GeneralProperties(interpreter.obtainGenProps());
        } catch (IOException e) {
            System.err.println("The program has been terminated because the file " + GeneralProperties.default_properties + " was not found, or the properties passed by the user were not valid: " + e.getMessage());
            return;
        }

        //3. Obtain the path of the serialized file
        File serializedFile;
        try {
            String filePath = PathManager.getSerializedFile(totalProperties.getCommonFormat());
            serializedFile = new File(filePath);
        } catch (IOException e) {
            System.err.println("Error: it was not possible to create the serialized file.");
            return;
        }

        //4. Instantiate the DeserializersContainer
        try {
            DeserializationHandler.setProperties(interpreter.obtainDeserProps());
        } catch (IOException e) {
            System.err.println("The program has been terminated because the file " + DeserializationProperties.default_properties + " was not found, or the properties passed by the user were not valid: " + e.getMessage());
            return;
        }
        if (interpreter.obtainSetFields()) {
            DeserializationHandler.deserializerSetFields();
        }

        if (interpreter.downloadPhase()) {
            // FIRST PHASE: download from sources
            //Obtains the properties from the command line, if specified
            String folderPath = interpreter.obtainPathOption();
            //If it was not specified, it calls an API.
            if (folderPath == null) {
                folderPath = downloadFromAPI(interpreter);
            }
            if (folderPath == null) {
                //Something went wrong during the call of the API
                return;
            }

            // SECOND PHASE: serialization to common format
            // 2.1 DESERIALIZATION from the given format to a List of Objects
            List<Serializable> articles = deserializeFolder(folderPath);
            if (articles == null) {
                return;
            }
            // 2.2 SERIALIZATION to the List of Objects to the common format
            serialize(interpreter, articles, serializedFile);
        }
        if (interpreter.analyzePhase()) {
            if (!serializedFile.exists())
            {
                System.err.println("Error: it was required the analysis of a file that do not exist");
                return;
            }

            // FIRST PHASE: deserialization from common format to a list of Objects
            List<UnitOfSearch> articles = deserializeCommonFile(serializedFile);
            if (articles == null)
            {
                return;
            }

            // SECOND PHASE: analyze the articles to obtain the most important words
            //Obtains the properties from the command line, if specified.
            int count = interpreter.obtainNumberOption();
            if (count <= 0) {
                count = totalProperties.getWordsCount();
            }
            analyze(interpreter, count, articles);

        }
        System.out.println("Everything went correctly.\nThank you for choosing our application, we hope to see you soon.");
    }

    private static String downloadFromAPI(CommandLineInterpreter interpreter)
    {
        // Folder to serialize.
        String folderPath;

        System.out.println("Entering the download part...");
        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            folderPath = DownloadHandler.download(interpreter.obtainDownProps(), interpreter.obtainAPIProps());
        }
        catch (IOException e)
        {
            System.err.println("The program has been terminated because the file "+ DownloadProperties.default_properties+" was not found, or the properties passed by the user were not valid: "+e.getMessage());
            return null;
        }

        System.out.println("Exiting the download part...\n");
        return folderPath;
    }

    private static List<Serializable> deserializeFolder(String folderPath)
    {
        System.out.println("\nEntering the deserialization of " + folderPath + "...");

        //Tries to deserialize the specified folder.
        List<Serializable> articles;
        try {
            articles = DeserializationHandler.deserializeFolder(folderPath);
        } catch (IOException e) {
            System.err.println("The program has been terminated because there was an error in the deserialization: " + e.getMessage());
            return null;
        }

        System.out.println("Exiting the deserialization part...\n");
        return articles;
    }

    public static void serialize(CommandLineInterpreter interpreter, List<Serializable> articles, File serializedFile)
    {
        System.out.println("\nEntering the serialization part...");

        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            SerializationHandler.setProperties(interpreter.obtainSerProps());
            SerializationHandler.serializeObjects(articles, serializedFile);
        } catch (IOException e) {
            System.err.println("Error during the serialization: " + e.getMessage());
            return;
        }

        System.out.println("Exiting the serialization part. You can find the serialized file in " + serializedFile.getPath() + "...\n");

    }

    public static List<UnitOfSearch> deserializeCommonFile(File serializedFile)
    {
        System.out.println("\nEntering the deserialization of " + serializedFile.getPath() + "...");

        List<Serializable> articles;
        try {
            articles = DeserializationHandler.deserializeFile(serializedFile);
        } catch (IOException e) {
            System.err.println("Error during the deserialization of the common format: " + e.getMessage());
            return null;
        }

        List<UnitOfSearch> unitOfSearches = new ArrayList<>();
        for (Serializable s : articles)
        {
            if (!(s instanceof UnitOfSearch))
            {
                System.err.println("Error during the deserialization of the common format: the articles are not analyzable");
                return null;
            }
            unitOfSearches.add((UnitOfSearch) s);
        }
        System.out.println("Exiting the deserialization part...\n");
        return unitOfSearches;
    }

    private static void analyze(CommandLineInterpreter interpreter, int count, List<UnitOfSearch> unitOfSearches)
    {
        System.out.println("\nEntering the analysis part...");

        String out_file;
        try {
            //Obtains the properties from the command line, if specified, and calls the handler.
            out_file = AnalyzerHandler.analyze(interpreter.obtainAnalyzeProps(), unitOfSearches, count, interpreter.obtainStopWords());
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("The program has been terminated for an error in the analysis: " + e.getMessage());
            return;
        }
        System.out.println("Exiting the analysis part. You can find the resulting file in" + out_file + "\n");
    }
}