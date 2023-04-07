package com.digdes.school;

import com.digdes.school.entity.Table;
import com.digdes.school.execution.QueryExecutorImpl;
import com.digdes.school.utils.input.ConsoleReader;
import com.digdes.school.utils.input.ConsoleReaderImpl;
import com.digdes.school.utils.output.ConsoleWriterImpl;

import java.util.List;

public class JavaSchoolStarter {
    public static void main(String[] args) {
        String tableName = "Users";
        List<String> tableSchema = List.of("id", "lastName", "age", "cost", "active");

        Table.createTable(tableName, tableSchema);

        ConsoleReader reader = new ConsoleReaderImpl(new QueryExecutorImpl(), new ConsoleWriterImpl());
        reader.read();
    }
}
