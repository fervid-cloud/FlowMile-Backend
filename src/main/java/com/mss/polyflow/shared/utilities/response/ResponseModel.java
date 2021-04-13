package com.mss.polyflow.shared.utilities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseModel {

    private static final Object SUCCESS = 1;

    private String message;

    private Object data;

    private Long status;

    public static ResponseEntity<Object> sendResponse(Object data, String message) {
        return new ResponseEntity(new ResponseModel()
                                      .setData(data)
                                      .setMessage(message)
                                      .setData(SUCCESS), HttpStatus.OK);
    }

}
