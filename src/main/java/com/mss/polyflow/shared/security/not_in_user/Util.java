package com.mss.polyflow.shared.security.not_in_user;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public static void showValueStatus(HttpServletRequest request, Object currentObject) {
        Integer currentValue = (Integer)request.getAttribute("counterValue");
        int value = currentValue != null ? currentValue : 0;
        log.info("#####################################################################");
        log.info("CURRENT VALUE IS ---------------------------------in {} :  {}", currentObject.getClass().getName(), value);
        request.setAttribute("counterValue", ++value);
    }
}
