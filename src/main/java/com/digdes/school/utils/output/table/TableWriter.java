package com.digdes.school.utils.output.table;

import java.util.List;

public interface TableWriter {
    void writeTable(String selector, List<Integer> modifiedRows);
}
