package com.digdes.school.utils.output.table;

import com.digdes.school.entity.Table;
import com.digdes.school.utils.output.table.counter.TableCounter;
import com.digdes.school.utils.output.table.counter.TableCounterImpl;
import com.digdes.school.utils.output.table.decorator.*;

import java.util.List;
import java.util.Map;

public class TableWriterImpl implements TableWriter{
    private final TableCounter tableCounter;
    private final String tableName;
    private final List<String> schema;
    private final List<Map<String, Object>> data;
    private final Printer consolePrinter;
    private final Printer leftBorder;
    private final Printer rightBorder;
    private final HorizontalBorder horizontalBorder;

    public TableWriterImpl() {
        tableName = Table.getTable().getName();
        schema = Table.getTable().getSchema();
        data = Table.getTable().getData();

        tableCounter = new TableCounterImpl();

        consolePrinter = new PrinterImpl();
        leftBorder = new LeftBorder(consolePrinter);
        rightBorder = new RightBorder(consolePrinter);
        horizontalBorder = new HorizontalBorder();
    }

    @Override
    public void writeTable(String selector, List<Integer> modifiedRows) {
        Map<String, Integer> lengthColumnData = tableCounter.countLengthColumnData(data, schema);
        int tableWidth = tableCounter.countTableWidth(lengthColumnData);

        writeHead(lengthColumnData, tableWidth);
        writeBody(selector, lengthColumnData, tableWidth, modifiedRows);
    }

    private void writeHead(Map<String, Integer> lengthColumnData, int tableWidth) {
        System.out.println("draw 1. Table " + tableName);

        horizontalBorder.print(tableWidth);

        for (String column : schema) {
            int elementLength = lengthColumnData.get(column.toLowerCase());

            if (column.equalsIgnoreCase("active")) {
                rightBorder.print(column, elementLength, false);
            } else {
                leftBorder.print(column, elementLength, false);
            }
        }

        horizontalBorder.print(tableWidth);
    }

    private void writeBody(String selector, Map<String, Integer> lengthColumnData, int tableWidth, List<Integer> modifiedRows) {
        if (selector.equals("select")) {
            for (Integer selectedRow : modifiedRows) {
                Map<String, Object> rowData = data.get(selectedRow);

                for (String column : schema) {
                    String element = String.valueOf(rowData.get(column.toLowerCase()));
                    int elementLength = lengthColumnData.get(column.toLowerCase());

                    if (column.equalsIgnoreCase("active")) {
                        rightBorder.print(element, elementLength, false);
                    } else {
                        leftBorder.print(element, elementLength, false);
                    }
                }

                horizontalBorder.print(tableWidth);
            }
        } else {
            for (int rowNumber = 0; rowNumber < data.size(); rowNumber++) {
                Map<String, Object> row = data.get(rowNumber);

                for (String column : schema) {
                    String columnValue = String.valueOf(row.get(column.toLowerCase()));
                    int columnLength = lengthColumnData.get(column.toLowerCase());

                    if (column.equalsIgnoreCase("active")) {
                        rightBorder.print(columnValue, columnLength, modifiedRows.contains(rowNumber));
                    } else {
                        leftBorder.print(columnValue, columnLength, false);
                    }
                }

                horizontalBorder.print(tableWidth);
            }
        }
    }
}
