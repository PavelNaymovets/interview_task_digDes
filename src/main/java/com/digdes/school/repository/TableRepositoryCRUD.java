package com.digdes.school.repository;

import java.util.List;
import java.util.Map;

public interface TableRepositoryCRUD {
    Map<String, Object> insert(List<String> params);
    Map<String, Object> select(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount);
    Map<String, Object> update(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount);
    Map<String, Object> delete(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount);
}
