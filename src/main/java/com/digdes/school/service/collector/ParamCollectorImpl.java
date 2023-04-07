package com.digdes.school.service.collector;

import com.digdes.school.exception.QueryValidationException;

import java.util.List;

public class ParamCollectorImpl implements ParamCollector {
    @Override
    public void takeParamsWithWhere(String selector, int indexWordWhere, List<String> query, List<String> params, List<String> whereParams) {
        if (selector.equals("select") || selector.equals("delete")) {
            for (int i = 1; i < query.size(); i++) { //Select ..., Delete ... Не берем селектор. Поэтому i = 1
                if (i < indexWordWhere) {
                    params.add(query.get(i));
                } else if (i > indexWordWhere) {
                    whereParams.add(query.get(i));
                }
            }
        } else if (selector.equals("insert") || selector.equals("update")) {
            if (query.size() == 1) {
                throw new QueryValidationException("Error query. No params");
            }
            if (!query.get(1).equalsIgnoreCase("values")) {
                throw new QueryValidationException("Error query. No correct key word: " + query.get(1));
            }

            for (int i = 2; i < query.size(); i++) { //Insert Values ..., Update Values ... Не берем селектор. Поэтому i = 2
                if (i < indexWordWhere) {
                    params.add(query.get(i));
                } else if (i > indexWordWhere) {
                    whereParams.add(query.get(i));
                }
            }
        }
    }

    @Override
    public void takeParamsWithoutWhere(String selector, List<String> query, List<String> params) {
        if (selector.equals("select") || selector.equals("delete")) {
            for (int i = 1; i < query.size(); i++) {
                params.add(query.get(i));
            }
        } else if (selector.equals("insert") || selector.equals("update")) {
            if (query.size() == 1) {
                throw new QueryValidationException("Error query. No params");
            }
            if (!query.get(1).equalsIgnoreCase("values")) {
                throw new QueryValidationException("Error query. No correct key word: " + query.get(1));
            }

            for (int i = 2; i < query.size(); i++) {
                params.add(query.get(i));
            }
        }
    }

    @Override
    public void takeParamsBeforeAndAfterOrKeyWord(List<String> whereParams, int indexWordOrInWhereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr) {
        for (int i = 0; i < whereParams.size(); i++) {
            if (i < indexWordOrInWhereParams) {
                paramsBeforeOr.add(whereParams.get(i));
            } else {
                paramsAfterOr.add(whereParams.get(i));
            }
        }

        paramsBeforeOr.removeIf(param -> param.equals("or") || param.equals("and"));
        paramsAfterOr.removeIf(param -> param.equals("or") || param.equals("and"));
    }
}
