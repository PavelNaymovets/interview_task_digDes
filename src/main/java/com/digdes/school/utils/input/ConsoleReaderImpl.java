package com.digdes.school.utils.input;

import com.digdes.school.execution.QueryExecutor;
import com.digdes.school.utils.output.ConsoleWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class ConsoleReaderImpl implements ConsoleReader {

    private final QueryExecutor executor;
    private final ConsoleWriter writer;

    public ConsoleReaderImpl(QueryExecutor executor, ConsoleWriter writer) {
        this.executor = executor;
        this.writer = writer;
    }

    @Override
    public void read() {
        Map<String, Object> modifiedRows;
        String consoleMessage;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!(consoleMessage = bufferedReader.readLine()).equalsIgnoreCase("ESQ")) {
                modifiedRows = executor.execute(consoleMessage);
                writer.write(modifiedRows);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
