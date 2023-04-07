package com.digdes.school.service.authenticator;

import java.util.List;

public interface ParamAuthenticator {
    void authenticate(List<String> query,
                      List<String> params,
                      List<String> whereParams,
                      List<String> paramsBeforeOr,
                      List<String> paramsAfterOr,
                      List<Integer> requestConditionsCount);
}
