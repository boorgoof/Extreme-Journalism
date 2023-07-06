package it.unipd.dei.dbdc.tools;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

/**
 * This class uses {@link CommandLine} of the library org.apache.commons.cli to interpret
 * the arguments passed on the command line by the user.
 *
 * @see CommandLine
 */
public class CommandLineInterpreter {
    /**
     * The {@link CommandLine} that has the arguments passed by the user.
     * If there are no arguments, or if an Exception is thrown during the parsing,
     * it is null.
     *
     */
    private final CommandLine cmd;
    /**
     * The possible {@link Options} the user can pass. These are initialized in the
     * {@link CommandLineInterpreter#defineOptions()} function.
     *
     */
    private final Options options;
    /**
     * The {@link HelpFormatter} that is used to print the possible options, if the user
     * asks it or if there are no arguments passed.
     *
     */
    private final static HelpFormatter formatter = new HelpFormatter();

    /**
     * An array of the {@link Option} that specifies the possible actions of the application.
     * These options are mandatory, as defined in the {@link CommandLineInterpreter#defineOptions()} function.
     *
     */
    private final static Option[] actions = {
            new Option("h", "help", false, "Print help"),
            new Option("d", "download-files", false, "Download files from the selected API or serializes the files indicated in -path"),
            new Option("a", "analysis-terms", false, "Analyze the top terms of the common format file"),
            new Option("da", "download-and-analysis", false, "Download files from the selected API and analysis the top terms of those files")
    };

    /**
     * An array of the {@link Option} that can be specified for the download part.
     * They are used only if the option -d or -da is specified.
     *
     */
    private final static Option[] download = {
            new Option("apf", "api-properties-file", true, "Contains the path to the properties of the API to call"),
            new Option("dowpf", "download-properties-file", true, "Contains the path to the properties file that contains the managers that is possible to call"),
            new Option("path", "folder-path", true, "Contains the location of the place to take the files from to serialize them to common format"),
    };

    /**
     * An array of the {@link Option} that can be specified for the analysis part.
     * They are used only if the option -a or -da is specified.
     *
     */
    private final static Option[] analysis = {
            new Option("anapf", "analysis-properties-file", true, "Contains the path to the properties file that contains the analyzer to use for the extraction"),
            new Option("n", "number", true, "Contains the positive number of terms you want to have in the final output"),
            new Option("stop", "enable-stop-words", true, "True if you want to enable the stop-words in the analysis")
    };

    /**
     * An array of {@link Option} that can be specified to modify the general properties of the application.
     *
     */
    private final static Option[] general = {
            new Option("genpf", "general-properties-file", true, "Contains the path to the properties file that contains the common format and the number of terms to extract"),
            new Option("despf", "deserializers-properties-file", true, "Contains the path to the properties file that contains the deserializers to use"),
            new Option("serpf", "serializers-properties-file", true, "Contains the path to the properties file that contains the serializers to use"),
            new Option("setfi", "set-deserializers-fields", true, "A boolean indicating if you want to select interactively the fields of the deserializers to use")
    };

    /**
     * The constructor, which defines the possible {@link Option} of the command line,
     * tries to parse the ones passed by the user and throw an {@link IllegalArgumentException} if there is
     * an error in the parsing. If the exception is thrown, the object is not created and it is initialized as null.
     *
     * @param args The arguments passed by the user to the command line
     * @throws IllegalArgumentException If the user hasn't specified anything or there is an error in the parsing of the options.
     */
    public CommandLineInterpreter(String[] args) {
        options = defineOptions();
        cmd = parse(options, args);
        if (cmd == null)
        {
            formatter.printHelp("java -jar Extreme_journalism-1.0-SNAPSHOT-jar-with-dependencies.jar -{h, d, a, da} -{options} [value]", options);
            throw new IllegalArgumentException();
        }
    }

    /**
     * The first phase of the parsing: the definition of the options.
     * It uses the {@link Option} specified as static fields in this class.
     *
     */
    private Options defineOptions() {
        Options opt = new Options();
        // Add the mandatory actions to an OptionGroup
        OptionGroup actionGroup = new OptionGroup();
        for (Option op : actions) {
            actionGroup.addOption(op);
        }

        // Set the options as required
        actionGroup.setRequired(true);
        opt.addOptionGroup(actionGroup);

        // Download options
        for (Option op : download) {
            opt.addOption(op);
        }

        // Analysis options
        for (Option op : analysis) {
            opt.addOption(op);
        }

        // General options
        for (Option op : general) {
            opt.addOption(op);
        }
        return opt;
    }

    /**
     * The second phase of the parsing: the parsing of the options.
     * The parsing stage is where the text passed into the application via the command line is processed.
     * The resulting object is a {@link CommandLine}, and inside the function is used the {@link DefaultParser}.
     *
     * @param args The options specified by the user
     * @return A {@link CommandLine} that has all the options passed by the user
     * @throws IllegalArgumentException If the user hasn't specified anything or there is an error in the parsing of the options.
     */
    private static CommandLine parse(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException | NullPointerException ex) {
            System.err.println("ERROR - parsing command line:\n"+ex.getMessage());
        }
        return cmd;
    }

    // This is the third phase: the interrogation.

    /**
     * It returns true if the option -h was specified, and prints the possible options.
     *
     * @return True if the option -h was specified
     */
    public boolean help() {
        if (cmd.hasOption("h")) {
            formatter.printHelp("java -jar Extreme_journalism-1.0-SNAPSHOT-jar-with-dependencies.jar -{required one of these: h, d, a, da} -{not required options} [value]", options);
            return true;
        }
        return false;
    }

    /**
     * It returns true if the option -d or -da was specified, and prints the possible options.
     *
     * @return True if the option -d or -da was specified
     */
    public boolean downloadPhase() {
        return cmd.hasOption("d") || cmd.hasOption("da");
    }

    /**
     * It returns true if the option -h was specified, and prints the possible options.
     *
     * @return True if the option -h was specified
     */
    public boolean analyzePhase() {
        return cmd.hasOption("a") || cmd.hasOption("da");
    }

    // INTERROGATION OF THE PROPERTIES OPTIONS

    /**
     * It returns the value specified with the key -apf.
     *
     * @return The value specified with the key -apf, null if not present
     */
    public String obtainAPIProps() {
        return cmd.getOptionValue("apf");
    }

    /**
     * It returns the value specified with the key -despf.
     *
     * @return The value specified with the key -despf, null if not present
     */
    public String obtainDeserProps(){
        return cmd.getOptionValue("despf");
    }

    /**
     * It returns the value specified with the key -apf.
     *
     * @return The value specified with the key -apf, null if not present
     */
    public String obtainSerProps(){
        return cmd.getOptionValue("serpf");
    }

    /**
     * It returns the value specified with the key -dowpf.
     *
     * @return The value specified with the key -dowpf, null if not present
     */
    public String obtainDownProps() {
        return cmd.getOptionValue("dowpf");
    }

    /**
     * It returns the value specified with the key -genpf.
     *
     * @return The value specified with the key -genpf, null if not present
     */
    public String obtainGenProps() {
        return cmd.getOptionValue("genpf");
    }

    /**
     * It returns the value specified with the key -anapf.
     *
     * @return The value specified with the key -anapf, null if not present
     */
    public String obtainAnalyzeProps() {
        return cmd.getOptionValue("anapf");
    }

    /**
     * It returns the value specified with the key -setfi, by default false.
     *
     * @return True only if the value true is specified, false by default.
     */
    public boolean obtainSetFields()
    {
        return cmd.hasOption("setfi") && cmd.getOptionValue("setfi").equalsIgnoreCase("true");
    }

    /**
     * It returns the value specified with the key -path.
     *
     * @return The value specified with the key -path, null if not present
     */
    public String obtainPathOption() {
        return cmd.getOptionValue("path");
    }

    /**
     * It returns the value specified with the key -n.
     *
     * @return The value specified with the key -n as an Integer, -1 if not present
     */
    public int obtainNumberOption()
    {
        try
        {
            return Integer.parseInt(cmd.getOptionValue("n"));
         }
        catch(NumberFormatException e)
        {
            return -1;
        }
    }

    /**
     * It returns the value specified with the key -stop. The default value is true.
     *
     * @return The value specified with the key -stop, true if not present
     */
    public boolean obtainStopWords()
    {
        return !(cmd.hasOption("stop") && cmd.getOptionValue("stop").equalsIgnoreCase("false"));
    }
}