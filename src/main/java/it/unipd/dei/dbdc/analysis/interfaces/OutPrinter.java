package it.unipd.dei.dbdc.analysis.interfaces;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.io.IOException;
import java.util.List;

/**
 * This interface is the one that should be implemented by any class that prints the output file.
 * It provides only a function, and is useful as it follows the Strategy design pattern.
 *
 */
public interface OutPrinter {

    /**
     * The function that saves the most important terms into a file.
     *
     * @param max The {@link List} of terms to print, that are already in order.
     * @throws IOException If it can't print the file
     * @throws IllegalArgumentException If the {@link List} of terms to print is empty or null.
     * @return The path to the printed file.
     */
    String outFile(List<OrderedEntryStringInt> max) throws IOException, IllegalArgumentException;
}
