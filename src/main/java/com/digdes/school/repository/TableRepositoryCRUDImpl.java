package com.digdes.school.repository;

import com.digdes.school.repository.query.DeleteQuery;
import com.digdes.school.repository.query.InsertQuery;
import com.digdes.school.repository.query.SelectQuery;
import com.digdes.school.repository.query.UpdateQuery;

import java.util.List;
import java.util.Map;

public class TableRepositoryCRUDImpl implements TableRepositoryCRUD {
    private final InsertQuery insertQuery;
    private final SelectQuery selectQuery;
    private final UpdateQuery updateQuery;
    private final DeleteQuery deleteQuery;

    public TableRepositoryCRUDImpl() {
        insertQuery = new InsertQuery();
        selectQuery = new SelectQuery();
        updateQuery = new UpdateQuery();
        deleteQuery = new DeleteQuery();
    }

    @Override
    public Map<String, Object> insert(List<String> params) {
        return insertQuery.execute(params);
    }

    @Override
    public Map<String, Object> select(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        return selectQuery.execute(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
    }

    @Override
    public Map<String, Object> update(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        return updateQuery.execute(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
    }

    @Override
    public Map<String, Object> delete(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        return deleteQuery.execute(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
    }
}
