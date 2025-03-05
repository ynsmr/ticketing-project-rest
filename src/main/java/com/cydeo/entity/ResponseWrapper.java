package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseWrapper {

    private boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ResponseWrapper(Object data, String message, HttpStatus httpStatus) {
        this.data = data;
        this.message = message;
        this.code = httpStatus.value();
        this.success = true;
    }

    //This method is for controllers that do not return response body, e.g. - DELETE
    public ResponseWrapper(String message) {
        this.message = message;
        this.code = HttpStatus.OK.value();
        this.success = true;

    }
}
