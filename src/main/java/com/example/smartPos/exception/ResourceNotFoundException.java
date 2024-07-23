package com.example.smartPos.exception;

import com.example.smartPos.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will throw when there is a validation fail
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(ErrorCodes errorCode) {
        super(errorCode.description);
    }

}
