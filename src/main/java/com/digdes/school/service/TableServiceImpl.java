package com.digdes.school.service;

import com.digdes.school.exception.QueryValidationException;
import com.digdes.school.repository.TableRepositoryCRUD;
import com.digdes.school.repository.TableRepositoryCRUDImpl;
import com.digdes.school.service.authenticator.ParamAuthenticator;
import com.digdes.school.service.authenticator.ParamAuthenticatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableServiceImpl implements TableService {
    private final TableRepositoryCRUD tableRepository;
    private final ParamAuthenticator paramAuthenticator;
    private final List<String> params;
    private final List<String> whereParams;
    private final List<String> paramsBeforeOr;
    private final List<String> paramsAfterOr;
    private final List<Integer> requestConditionsCount;

    public TableServiceImpl() {
        tableRepository = new TableRepositoryCRUDImpl();
        paramAuthenticator = new ParamAuthenticatorImpl();
        params = new ArrayList<>();
        whereParams = new ArrayList<>();
        paramsBeforeOr = new ArrayList<>();
        paramsAfterOr = new ArrayList<>();
        requestConditionsCount = new ArrayList<>();
    }

    @Override
    public Map<String, Object> executeQuery(List<String> query) throws Exception {
        paramAuthenticator.authenticate(query, params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
        int indexWordWhere = query.indexOf("where");

        String selector = query.get(0);

        switch (selector) {
            case "select":
                return tableRepository.select(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
            case "update":
                return tableRepository.update(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
            case "delete":
                if (indexWordWhere > 0 && params.isEmpty() && whereParams.isEmpty()) {
                    throw new QueryValidationException("No correct query. No parameters before and after 'where' key word.");
                }

                return tableRepository.delete(params, whereParams, paramsBeforeOr, paramsAfterOr, requestConditionsCount);
            case "insert":

                if (indexWordWhere > 0) {
                    throw new QueryValidationException("Error query. It can not have key word: " + query.get(indexWordWhere));
                }

                return tableRepository.insert(params);
            default:
                throw new QueryValidationException("Error query. It can not have selector: " + selector);
        }
    }

    @Override
    public void clearAllParams() {
        params.clear();
        whereParams.clear();
        paramsBeforeOr.clear();
        paramsAfterOr.clear();
    }
}
