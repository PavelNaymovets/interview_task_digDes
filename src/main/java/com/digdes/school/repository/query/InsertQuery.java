package com.digdes.school.repository.query;

import com.digdes.school.exception.QueryValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertQuery extends Query {

    public Map<String, Object> execute(List<String> params) {
        if (params.isEmpty()) {
            throw new QueryValidationException("Error query. params is empty.");
        }

        Map<String, Object> row = new HashMap<>();
        putParamsInRow(params, row);

        data.add(row);
        indexModifiedRows.add(data.lastIndexOf(row));

        Map<String, Object> operationResult = prepareOperationResult("insert");
        clearParams(params);

        return operationResult;
    }

    private void clearParams(List<String> params) {
        params.clear();
        indexModifiedRows.clear();
    }
}
