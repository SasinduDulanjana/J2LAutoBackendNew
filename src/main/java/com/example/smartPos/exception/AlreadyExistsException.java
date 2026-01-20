package com.example.smartPos.exception;

import com.example.smartPos.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will throw when there is a validation fail
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(ErrorCodes errorCode) {
        super(errorCode.description);
    }

}
