package com.digdes.school.service.counter;

import java.util.List;

public interface ParamCounter {
    void countConditionsWithOrKeyWord(List<String> whereParams, int indexWordOrInWhereParams, List<Integer> requestConditionsCount);
    void countConditionsWithAndKeyWordComaDelimiter(List<String> whereParams, List<Integer> requestConditionsCount);
    void checkParamsQuantityBeforeWhere(List<String> params);
}
