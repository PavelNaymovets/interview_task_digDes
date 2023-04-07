package com.digdes.school.utils.output.table.counter;

import java.util.List;
import java.util.Map;

public interface TableCounter {
    Map<String, Integer> countLengthColumnData(List<Map<String, Object>> data, List<String> schema);
    int countTableWidth(Map<String, Integer> lengthColumnData);
}
