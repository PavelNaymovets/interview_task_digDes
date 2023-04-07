package com.digdes.school.repository.query;

import com.digdes.school.exception.QueryValidationException;

import java.util.List;
import java.util.Map;

public class SelectQuery extends Query {
    public Map<String, Object> execute(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        if (!params.isEmpty() && !whereParams.isEmpty()) {
            throw new QueryValidationException("Error query. Query can not to have params before 'where'");
        }

        if ((params.isEmpty() || (params.size() == 1 && params.contains("*"))) && whereParams.isEmpty()) {
            selectAll();
        } else {
            executeWithOrWithoutOr("select", params, whereParams, paramsBeforeOr, paramsAfterOr,indexModifiedRows, requestConditionsCount);
        }

        Map<String, Object> operationResult = prepareOperationResult("select");
        clearParams(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);

        return operationResult;
    }

    private void selectAll() {
        for (int i = 0; i < data.size(); i++) {
            indexModifiedRows.add(i);
        }
    }
}
