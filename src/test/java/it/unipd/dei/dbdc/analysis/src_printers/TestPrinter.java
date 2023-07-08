package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.AnalyzerHandlerTest;
import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.OutPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Utility class identical to {@link TxtOutPrinter}. It is not tested as its function is in {@link TxtOutPrinterTest}.
 */
public class TestPrinter implements OutPrinter {

    /**
     * Default constructor of the class. It is the only constructor, as there are
     * no fields to give value to, so it doesn't do anything.
     * This is required as the interface {@link OutPrinter} can only give non-static
     * methods to be implemented by other classes.
     *
     */
    public TestPrinter() {}

    /**
     * The function that prints the most important terms into a file .txt.
     *
     * @param max The {@link List} of terms to print, that are already in order.
     * @return The path of the file printed
     * @throws IOException If it can't print the file
     * @throws IllegalArgumentException If the {@link List} of terms to print is empty or null.
     */
    @Override
    public String outFile(List<OrderedEntryStringInt> max) throws IOException, IllegalArgumentException {
        if (max == null || max.isEmpty())
        {
            throw new IllegalArgumentException("Vector of most important words is empty");
        }
        String file_name = AnalyzerHandlerTest.resources_url+"output.txt";
        try (PrintWriter writer = new PrintWriter(file_name)) {
            for (int i = 0; i <max.size(); i++) {
                OrderedEntryStringInt el = max.get(i);
                writer.print(el.getKey() + " " + el.getValue());
                if (i != max.size()-1) {
                    writer.println();
                }
            }
        }
        return file_name;
    }
}