package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.OutPrinter;
import it.unipd.dei.dbdc.tools.PathTools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class implements {@link OutPrinter}.
 * It is a Strategy that prints the output file as a .txt file.
 *
 * @see OutPrinter
 */
public class TxtOutPrinter implements OutPrinter {
    /**
     * This is the extension of the file printed by this class.
     *
     */
    private static final String extension = ".txt";
    /**
     * The function that prints the most important terms into a file.
     *
     * @param max The {@link List} of terms to print, that are already in order.
     * @param outFilePath The path of the file to print, with the name but without the extension
     * @throws IOException If it can't print the file
     * @throws IllegalArgumentException If the {@link List} of terms to print is empty or null.
     */
    @Override
    public String outFile(List<OrderedEntryStringInt> max) throws IOException, IllegalArgumentException {
        if (max == null || max.isEmpty())
        {
            throw new IllegalArgumentException("Vector of most important words is empty");
        }
        String file_name = PathTools.getOutFile() +extension;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_name))) {
            for (int i = 0; i <max.size(); i++) {
                OrderedEntryStringInt el = max.get(i);
                writer.write(el.getKey() + " " + el.getValue());
                if (i != max.size()-1) {
                    writer.newLine();
                }
            }
        }
        return file_name;
    }
}
