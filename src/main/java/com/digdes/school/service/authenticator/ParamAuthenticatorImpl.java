package com.digdes.school.service.authenticator;

import com.digdes.school.exception.QueryValidationException;
import com.digdes.school.service.collector.ParamCollector;
import com.digdes.school.service.collector.ParamCollectorImpl;
import com.digdes.school.service.counter.ParamCounter;
import com.digdes.school.service.counter.ParamCounterImpl;

import java.util.List;

public class ParamAuthenticatorImpl implements ParamAuthenticator {
    private final ParamCollector paramCollector;
    private final ParamCounter paramCounter;

    public ParamAuthenticatorImpl() {
        paramCollector = new ParamCollectorImpl();
        paramCounter = new ParamCounterImpl();
    }

    @Override
    public void authenticate(
            List<String> query,
            List<String> params,
            List<String> whereParams,
            List<String> paramsBeforeOr,
            List<String> paramsAfterOr,
            List<Integer> requestConditionsCount
    ) {
        String selector = query.get(0);
        int indexWordWhere = query.indexOf("where");
        int indexWordOr = query.indexOf("or");

        if (indexWordWhere > 0) {
            paramCollector.takeParamsWithWhere(selector, indexWordWhere, query, params, whereParams);

            if (indexWordOr > 0 && indexWordOr != indexWordWhere && indexWordOr != indexWordWhere + 1) {
                indexWordOr = whereParams.indexOf("or");

                paramCollector.takeParamsBeforeAndAfterOrKeyWord(whereParams, indexWordOr, paramsBeforeOr, paramsAfterOr);
                paramCounter.countConditionsWithOrKeyWord(whereParams, indexWordOr, requestConditionsCount);
            } else if (indexWordOr < 0){
                paramCounter.countConditionsWithAndKeyWordComaDelimiter(whereParams, requestConditionsCount);
            } else {
                throw new QueryValidationException("Error query. Key word: or");
            }
        } else if (indexWordWhere < 0) {
            paramCollector.takeParamsWithoutWhere(selector, query, params);
        } else {
            throw new QueryValidationException("Error query. Key word: 'where' or 'or'");
        }

        paramCounter.checkParamsQuantityBeforeWhere(params); //Количество параметров перед ключевым словом WHERE не должно превышать 1 на каждый параметр.
    }
}
