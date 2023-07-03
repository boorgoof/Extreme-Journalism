package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.AnalyzerHandlerTest;
import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.OutPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//Utility class identical to TxtOutPrinter
public class TestPrinter implements OutPrinter {
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