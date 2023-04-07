package com.digdes.school.service.collector;

import java.util.List;

public interface ParamCollector {
    void takeParamsWithWhere(String selector, int indexWordWhere, List<String> query, List<String> params, List<String> whereParams);
    void takeParamsWithoutWhere(String selector, List<String> query, List<String> params);
    void takeParamsBeforeAndAfterOrKeyWord(List<String> whereParams, int indexWordOrInWhereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr);
}
