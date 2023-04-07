package com.digdes.school.repository.query;

import com.digdes.school.exception.QueryValidationException;

import java.util.List;
import java.util.Map;

public class UpdateQuery extends Query {
    public Map<String, Object> execute(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        if (params.isEmpty() && whereParams.isEmpty()) {
            throw new QueryValidationException("Error query. Query can not to be without params");
        }

        if (!params.isEmpty() && whereParams.isEmpty()) {
            updateAll(params);
        } else {
            executeWithOrWithoutOr("update", params, whereParams, paramsBeforeOr, paramsAfterOr, indexModifiedRows, requestConditionsCount);
        }

        Map<String, Object> operationResult = prepareOperationResult("update");
        clearParams(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);

        return operationResult;
    }

    protected void updateAll(List<String> params) {
        for (int rowNumber = 0; rowNumber < data.size(); rowNumber++) {
            Map<String, Object> row = data.get(rowNumber);

            putParamsInRow(params, row);

            indexModifiedRows.add(rowNumber);
        }
    }
}
