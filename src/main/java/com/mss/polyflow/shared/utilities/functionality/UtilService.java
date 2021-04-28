package com.mss.polyflow.shared.utilities.functionality;

public class UtilService {

    public static  String fromCamelCaseToSnakeCase(String orderCriteria) {
        StringBuilder updatedResultString = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        int n = orderCriteria.length();
        for(int i = 0; i < n; ++i) {
            char curChar = orderCriteria.charAt(i);
            if(curChar >= 'A' && curChar <= 'Z') {
                if(updatedResultString.length() > 0) {
                    updatedResultString.append("_");
                }
                updatedResultString.append(temp);
                temp.setLength(0);
                curChar = (char)(curChar + 32);
            }
            temp.append(curChar);
        }

        if(updatedResultString.length() > 0) {
            updatedResultString.append("_");
        }
        updatedResultString.append(temp);

        return updatedResultString.toString();
    }
}
