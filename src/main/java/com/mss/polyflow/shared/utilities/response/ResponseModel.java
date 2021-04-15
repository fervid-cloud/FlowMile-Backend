package com.mss.polyflow.shared.utilities.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseModel {

    private static final Integer SUCCESS = 1;

    private String message;

    private Object data;

    private Integer status;

    public static ResponseEntity<Object> sendResponse(Object data, String message) {
        return new ResponseEntity(new ResponseModel()
                                      .setData(data)
                                      .setMessage(message)
                                      .setStatus(SUCCESS), HttpStatus.OK);
    }

}
