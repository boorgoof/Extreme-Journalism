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
    private static final Options options = new Options();
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
            new Option("d", "download-files", false, "Download files from the selected API"),
            new Option("a", "analysis-terms", false, "Analyze the top 50 terms of the selected files"),
            new Option("da", "download-and-analysis", false, "Download files from the selected API and analysis the top 50 terms of those files")
    };

    /**
     * An array of the {@link Option} that can be specified for the download part.
     * They are used only if the option -d or -da is specified.
     *
     */
    private final static Option[] download = {
            new Option("apf", "api-properties-file", true, "Contains the path to the properties of the API to call"),
            new Option("dowpf", "download-properties-file", true, "Contains the path to the properties file that contains the managers that is possible to call"),
    };

    /**
     * An array of the {@link Option} that can be specified for the analysis part.
     * They are used only if the option -a or -da is specified.
     *
     */
    private final static Option[] analysis = {
            new Option("anapf", "analysis-properties-file", true, "Contains the path to the properties file that contains the analyzer to use for the extraction"),
            new Option("despf", "deserializers-properties-file", true, "Contains the path to the properties file that contains the deserializers to use"),
            new Option("serpf", "serializers-properties-file", true, "Contains the path to the properties file that contains the serializers to use"),
            new Option("path", "folder-path", true, "Contains the location of the place to take the files from"),
            new Option("n", "number", true, "Contains the number of terms you want to have in the final output"),
            new Option("stop", "enable-stop-words", true, "True if you want to enable the stop-words in the analysis")
    };

    /**
     * An {@link Option} that can be specified to modify the general properties of the application.
     *
     */
    private final static Option general = new Option("genpf", "general-properties-file", true, "Contains the path to the properties file that contains the common format and the number of terms to extract");

    /**
     * The constructor, which defines the possible {@link Option} of the command line,
     * tries to parse the ones passed by the user and throw an {@link IllegalArgumentException} if there is
     * an error in the parsing.
     *
     * @param args The arguments passed by the user to the command line
     * @throws IllegalArgumentException If the user hasn't specified anything or there is an error in the parsing of the options.
     */
    public CommandLineInterpreter(String[] args) {
        defineOptions();
        cmd = parse(args);
        if (cmd == null)
        {
            formatter.printHelp("App -{et} [options]", options);
            throw new IllegalArgumentException();
        }
    }

    /**
     * The first phase of the parsing: the definition of the options.
     * It uses the {@link Option} specified as static fields in this class.
     *
     */
    private static void defineOptions() {
        // Add the mandatory actions to an OptionGroup
        OptionGroup actionGroup = new OptionGroup();
        for (Option op : actions) {
            actionGroup.addOption(op);
        }

        // Set the options as required
        // actionGroup.setRequired(true); FIXME
        options.addOptionGroup(actionGroup);

        // Download options
        for (Option op : download) {
            options.addOption(op);
        }

        // Analysis options
        for (Option op : analysis) {
            options.addOption(op);
        }

        // General option
        options.addOption(general);
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
    public static CommandLine parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            System.err.println("ERROR - parsing command line:\n"+ex.getMessage());
        }
        return cmd;
    }

    // This is the third phase: the interrogation.

    //INTERROGATION OF ACTIONS

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns true if the option -h was specified, and prints the possible options.
     *
     * @return True if the option -h was specified
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public boolean help() throws NullPointerException {
        if (cmd.hasOption("h")) {
            formatter.printHelp("App -{et} [options]", options);
            return true;
        }
        return false;
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns true if the option -d or -da was specified, and prints the possible options.
     *
     * @return True if the option -d or -da was specified
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public boolean downloadPhase() throws NullPointerException {
        return true;
        // return cmd.hasOption("d") || cmd.hasOption("ds"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns true if the option -h was specified, and prints the possible options.
     *
     * @return True if the option -h was specified
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public boolean analyzePhase() throws NullPointerException {
        return true;
        //return cmd.hasOption("s") || cmd.hasOption("ds"); FIXME
    }

    // INTERROGATION OF THE PROPERTIES OPTIONS

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -apf.
     *
     * @return The value specified with the key -apf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainAPIProps() throws NullPointerException {
        return "./out_properties/api.properties";
        //return cmd.getOptionValue("apf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -despf.
     *
     * @return The value specified with the key -despf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainDeserProps() throws NullPointerException {
        return null;
        //return cmd.getOptionValue("despf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -apf.
     *
     * @return The value specified with the key -apf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainSerProps() throws NullPointerException {
        return null;
        //return cmd.getOptionValue("serpf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -dowpf.
     *
     * @return The value specified with the key -dowpf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainDownProps() throws NullPointerException {
        return null;
        //return cmd.getOptionValue("dowpf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -genpf.
     *
     * @return The value specified with the key -genpf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainGenProps() throws NullPointerException {
        return null;
        //return cmd.getOptionValue("genpf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -anapf.
     *
     * @return The value specified with the key -anapf, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainAnalyzeProps() throws NullPointerException {
        return null;
        //return cmd.getOptionValue("anapf"); FIXME
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -path.
     *
     * @return The value specified with the key -path, null if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public String obtainPathOption() throws NullPointerException {
        /*
        return cmd.getOptionValue("path"); FIXME
         */
        return "./database/nytimes_articles_v2";
        //return null;
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -n.
     *
     * @return The value specified with the key -n as an Integer, -1 if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     * @throws NumberFormatException If the specified value is not a number
     */
    public int obtainNumberOption() throws NullPointerException
    {
        return 50;
        /*
        try
        {
            return Integer.parseInt(cmd.getOptionValue("n")); FIXME
         }
         catch(...)
         {
            return -1;
         }
         */
    }

    /**
     * It should be called after being sure that the {@link CommandLine} of this class is not null.
     * It returns the value specified with the key -stop. The default value is true.
     *
     * @return The value specified with the key -stop, true if not present
     * @throws NullPointerException If the {@link CommandLine} of this class is null
     */
    public boolean obtainStopWords() throws NullPointerException
    {
        /*
        if (cmd.getOptionValue("stop").equalsIgnoreCase("false"))
        {
            return false;
        }
         */
        return true;
    }
}