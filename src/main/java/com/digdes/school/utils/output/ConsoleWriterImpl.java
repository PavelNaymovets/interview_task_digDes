package com.digdes.school.utils.output;

import com.digdes.school.utils.output.table.TableWriter;
import com.digdes.school.utils.output.table.TableWriterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsoleWriterImpl implements ConsoleWriter {
    private final TableWriter tableWriter;

    public ConsoleWriterImpl() {
        tableWriter = new TableWriterImpl();
    }

    @Override
    public void write(Map<String, Object> modifiedRowsAndSelector) {
        /*
         Если в другой части программы Exception:
         * modifiedRows присваиваю пустой лист;
         * selector присваиваю строку, с которой ничего кроме шапки не напечатается.
        */
        List<Integer> modifiedRows = new ArrayList<>();
        String selector = "exception";

        if (modifiedRowsAndSelector != null) {
            if (modifiedRowsAndSelector.get("modifiedRows") != null) {
                modifiedRows = (List<Integer>) modifiedRowsAndSelector.get("modifiedRows");
            }
            if (modifiedRowsAndSelector.get("selector") != null) {
                selector = (String) modifiedRowsAndSelector.get("selector");
            }
            modifiedRowsAndSelector.clear();

            if (selector.equals("delete") || !modifiedRows.isEmpty()) {
                tableWriter.writeTable(selector, modifiedRows);
            }
        }
    }
}
