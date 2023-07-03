package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandLineInterpreterTest {

    @Test
    public void CommandLineInterpreter()
    {
        //Test the exceptions
        final String[] arguments = new String[0];
        assertThrows(IllegalArgumentException.class, () -> new CommandLineInterpreter(null));
        assertThrows(IllegalArgumentException.class, () -> new CommandLineInterpreter(arguments));
        final String[] arguments1 = new String[1];
        assertThrows(IllegalArgumentException.class, () -> new CommandLineInterpreter(arguments1));
        final String[] arguments2 = {""};
        assertThrows(IllegalArgumentException.class, () -> new CommandLineInterpreter(arguments2));
        final String[] arguments3 = {"-urgaj"};
        assertThrows(IllegalArgumentException.class, () -> new CommandLineInterpreter(arguments3));
        CommandLineInterpreter commandLineInterpreter = null;
        try {
            commandLineInterpreter = new CommandLineInterpreter(null);
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }
        assertNull(commandLineInterpreter);

        //Tests that should not throw exceptions:
        assertDoesNotThrow(() ->
                {
                    String[] args = {"-h"};
                    CommandLineInterpreter cli = new CommandLineInterpreter(args);
                    //Phases
                    assertTrue(cli.help());
                    assertFalse(cli.downloadPhase());
                    assertFalse(cli.analyzePhase());
                    //Properties
                    assertNull(cli.obtainAPIProps());
                    assertNull(cli.obtainAnalyzeProps());
                    assertNull(cli.obtainDeserProps());
                    assertNull(cli.obtainDownProps());
                    assertNull(cli.obtainGenProps());
                    assertNull(cli.obtainSerProps());
                    //Other options
                    assertNull(cli.obtainPathOption());
                    assertTrue(cli.obtainStopWords());
                    assertEquals(-1, cli.obtainNumberOption());

                    String[] args1 = {"-d", "-apf", "aa", "-dowpf", "dd"};
                    cli = new CommandLineInterpreter(args1);
                    //Phases
                    assertFalse(cli.help());
                    assertTrue(cli.downloadPhase());
                    assertFalse(cli.analyzePhase());
                    //Properties
                    assertEquals("aa", cli.obtainAPIProps());
                    assertNull(cli.obtainAnalyzeProps());
                    assertNull(cli.obtainDeserProps());
                    assertEquals("dd", cli.obtainDownProps());
                    assertNull(cli.obtainGenProps());
                    assertNull(cli.obtainSerProps());
                    //Other options
                    assertNull(cli.obtainPathOption());
                    assertTrue(cli.obtainStopWords());
                    assertEquals(-1, cli.obtainNumberOption());

                    String[] args2 = {"-a", "-anapf", "an", "-n", "45", "-path", "uu", "-stop", "false"};
                    cli = new CommandLineInterpreter(args2);
                    //Phases
                    assertFalse(cli.help());
                    assertFalse(cli.downloadPhase());
                    assertTrue(cli.analyzePhase());
                    //Properties
                    assertNull(cli.obtainAPIProps());
                    assertEquals("an", cli.obtainAnalyzeProps());
                    assertNull(cli.obtainDeserProps());
                    assertNull(cli.obtainDownProps());
                    assertNull(cli.obtainGenProps());
                    assertNull(cli.obtainSerProps());
                    //Other options
                    assertEquals("uu", cli.obtainPathOption());
                    assertFalse(cli.obtainStopWords());
                    assertEquals(45, cli.obtainNumberOption());

                    String[] args3 = {"-da", "-genpf", "gg", "-despf", "dd", "-serpf", "ss"};
                    cli = new CommandLineInterpreter(args3);
                    //Phases
                    assertFalse(cli.help());
                    assertTrue(cli.downloadPhase());
                    assertTrue(cli.analyzePhase());
                    //Properties
                    assertNull(cli.obtainAPIProps());
                    assertNull(cli.obtainAnalyzeProps());
                    assertEquals("dd", cli.obtainDeserProps());
                    assertNull(cli.obtainDownProps());
                    assertEquals("gg", cli.obtainGenProps());
                    assertEquals("ss", cli.obtainSerProps());
                    //Other options
                    assertNull(cli.obtainPathOption());
                    assertTrue(cli.obtainStopWords());
                    assertEquals(-1, cli.obtainNumberOption());

                    //Test with various things
                    String[] args4 = {"-da", "-genpf", "gg", "-despf", "dd", "-serpf", "ss", "-anapf", "an", "-n", "34", "-path", "uu", "-stop", "false", "-apf", "aa", "-dowpf", "dd"};
                    cli = new CommandLineInterpreter(args4);
                    //Phases
                    assertFalse(cli.help());
                    assertTrue(cli.downloadPhase());
                    assertTrue(cli.analyzePhase());
                    //Properties
                    assertEquals("aa", cli.obtainAPIProps());
                    assertEquals("an", cli.obtainAnalyzeProps());
                    assertEquals("dd", cli.obtainDeserProps());
                    assertEquals("dd", cli.obtainDownProps());
                    assertEquals("gg", cli.obtainGenProps());
                    assertEquals("ss", cli.obtainSerProps());
                    //Other options
                    assertEquals("uu", cli.obtainPathOption());
                    assertFalse(cli.obtainStopWords());
                    assertEquals(34, cli.obtainNumberOption());

                    //Test with various things
                    String[] args5 = {"-da", "-n", "illegal"};
                    cli = new CommandLineInterpreter(args5);
                    //Phases
                    assertFalse(cli.help());
                    assertTrue(cli.downloadPhase());
                    assertTrue(cli.analyzePhase());
                    //Properties
                    assertNull(cli.obtainAPIProps());
                    assertNull(cli.obtainAnalyzeProps());
                    assertNull(cli.obtainDeserProps());
                    assertNull(cli.obtainDownProps());
                    assertNull(cli.obtainGenProps());
                    assertNull(cli.obtainSerProps());
                    //Other options
                    assertNull(cli.obtainPathOption());
                    assertTrue(cli.obtainStopWords());
                    assertEquals(-1, cli.obtainNumberOption());
                }
        );
    }
}
