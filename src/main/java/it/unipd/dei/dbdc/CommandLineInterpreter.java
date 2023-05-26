package it.unipd.dei.dbdc;

import org.apache.commons.cli.*;

public class CommandLineInterpreter {

    private static final Options options = new Options();
    private final static HelpFormatter formatter = new HelpFormatter();
    // There are three stages to command line processing.
    // They are the definition, parsing and interrogation stages.

    public static CommandLine parseCommandLine(String[] args)
    {
        defineOptions();
        CommandLine cmd = parse(args);
        if (cmd.hasOption("h")) {
            formatter.printHelp("App -{et} [options]", options);
            return null;
        }
        return cmd;
    }
    /*
    1. DEFINITION:
    Each command line must define the set of options that will be used to define the interface to the application.
    CLI uses the Options class, as a container for Option instances. There are two ways to create Options in CLI.
    One of them is via the constructors, the other way is via the factory methods defined in Options.
    The result of the definition stage is an Options instance.
     */
    private static void defineOptions() {

        // Add the possible actions to an OptionGroup
        OptionGroup actionGroup = new OptionGroup();

        actionGroup.addOption(new Option("h", "help", false, "Print help"));
        actionGroup.addOption(new Option("d", "download-files", true, "Download files from the selected API"));
        actionGroup.addOption(new Option("s", "search-terms", true, "Search the top 50 terms of the selected files"));
        actionGroup.addOption(new Option("ds", "download-and-search", true, "Download files from the selected API and search the top 50 terms of those files"));

        // Set the options as required
        actionGroup.setRequired(true);
        options.addOptionGroup(actionGroup);

        // Download options
        options.addOption("api", true, "Name of the api to download from");
        options.addOption("key", true, "Api-key for the download, if required");
        options.addOption("params", true, "Params for the query");
    }

    /*
    2. PARSING
    The parsing stage is where the text passed into the application via the command line is processed.
    The text is processed according to the rules defined by the parser implementation.
    The parse method defined on CommandLineParser takes an Options instance and a String[] of arguments and returns a CommandLine.
    The result of the parsing stage is a CommandLine instance.
     */
    public static CommandLine parse(String[] args) {
        // The HelpFormatter is to print help messages
        HelpFormatter formatter = new HelpFormatter();

        // There may be several implementations of the CommandLineParser interface, the recommended one is the DefaultParser
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            System.err.println("ERROR - parsing command line:");
            System.err.println(ex.getMessage());
            formatter.printHelp("App -{h,d,s,ds} [options]", options);
        }
        return cmd;
    }

    /*
    3. INTERROGATION
    The interrogation stage is where the application queries the CommandLine to decide what execution branch to take
    depending on boolean options and uses the option values to provide the application data.
    This stage is implemented in the user code. The accessor methods on CommandLine provide the interrogation capability to the user code.
    The result of the interrogation stage is that the user code is fully informed of all the text that was supplied
    on the command line and processed according to the parser and Options rules.
     */
    private static boolean[] interpret(CommandLine cmd)
    {
        boolean[] ret = {false, false};
        if (cmd.hasOption("h")) {
            formatter.printHelp("App -{et} [options]", options);
            return null;
        }
        else if (cmd.hasOption("d"))
        {
            ret[0] = true;
        }
        else if (cmd.hasOption("s"))
        {
            ret[1] = true;
        }
        else if (cmd.hasOption("ds"))
        {
            ret[0] = true;
            ret[1] = true;
        }
        return ret;
    }
}
