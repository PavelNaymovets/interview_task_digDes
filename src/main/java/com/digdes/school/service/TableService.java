package com.digdes.school.service;

import java.util.List;
import java.util.Map;

public interface TableService {
    Map<String, Object> executeQuery(List<String> query) throws Exception;
    void clearAllParams();
}
