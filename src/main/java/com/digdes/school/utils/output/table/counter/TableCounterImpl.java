package com.digdes.school.utils.output.table.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableCounterImpl implements TableCounter{
    private static final int PADDING = 8; //Учет отступов в таблице. По 4 с каждой стороны.

    @Override
    public Map<String, Integer> countLengthColumnData(List<Map<String, Object>> data, List<String> schema) {
        Map<String, Integer> lengthColumnData = new HashMap<>();

        for (String column : schema) {
            lengthColumnData.put(column.toLowerCase(), column.length());
        }

        for (Map<String, Object> row : data) {
            for (String column : row.keySet()) {
                int newLengthColumnValue = String.valueOf(row.get(column)).length();
                int oldLengthColumnValue = lengthColumnData.get(column);

                lengthColumnData.put(column, Math.max(newLengthColumnValue, oldLengthColumnValue));
            }
        }

        for (String column : lengthColumnData.keySet()) {
            int lengthColumnElement = lengthColumnData.get(column);
            lengthColumnElement = lengthColumnElement + PADDING;
            lengthColumnData.put(column, lengthColumnElement);
        }

        return lengthColumnData;
    }

    @Override
    public int countTableWidth(Map<String, Integer> lengthColumnData) {
        int tableWidth = 0;

        for (String column : lengthColumnData.keySet()) {
            tableWidth = tableWidth + lengthColumnData.get(column);
        }

        return tableWidth;
    }
}
