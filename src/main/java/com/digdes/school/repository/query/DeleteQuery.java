package com.digdes.school.repository.query;

import com.digdes.school.exception.QueryValidationException;

import java.util.List;
import java.util.Map;

public class DeleteQuery extends Query{
    public Map<String, Object> execute(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        if (!params.isEmpty() && !whereParams.isEmpty()) {
            throw new QueryValidationException("Error query. Query can not to have params before 'where'");
        }

        if ((params.isEmpty() || (params.size() == 1 && params.contains("*"))) && whereParams.isEmpty()) {
            deleteAll();
        } else {
            executeWithOrWithoutOr("delete", params, whereParams, paramsBeforeOr, paramsAfterOr, indexModifiedRows, requestConditionsCount);
            deleteCorrespondIndex();
        }

        clearParams(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);

        return prepareOperationResult("delete");
    }

    private void deleteCorrespondIndex() {
        if (!indexModifiedRows.isEmpty()) {
            for (int lastElement = indexModifiedRows.size() - 1; lastElement >= 0; lastElement--) {
                int indexElementForDelete = indexModifiedRows.get(lastElement);
                data.remove(indexElementForDelete);
            }
        }
    }

    private void deleteAll() {
        data.clear();
    }
}
