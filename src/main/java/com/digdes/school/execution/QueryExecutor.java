package com.digdes.school.execution;

import java.util.List;
import java.util.Map;

public interface QueryExecutor {
    Map<String, Object> execute(String query);
}
