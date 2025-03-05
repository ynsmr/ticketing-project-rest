package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

    private boolean success;
    private String message;
    private int code;
    private Object data;

    public ResponseWrapper(Object data, String message, HttpStatus httpStatus) {
        this.data = data;
        this.message = message;
        this.code = httpStatus.value();
        this.success = true;
    }

    //This method is for controllers that do not return response body, e.g. - DELETE
    public ResponseWrapper(String message, HttpStatus httpStatus) {
        this.message = message;
        this.code = httpStatus.value();
        this.success = true;

    }
}
