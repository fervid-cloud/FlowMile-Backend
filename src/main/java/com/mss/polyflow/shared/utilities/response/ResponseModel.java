package com.mss.polyflow.shared.utilities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseModel {

    private String message;
    
    private Object data;

    private Long status;

}
