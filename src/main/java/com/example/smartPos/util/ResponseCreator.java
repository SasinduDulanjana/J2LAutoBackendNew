package com.example.smartPos.util;


import com.example.smartPos.controllers.responses.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseCreator {
    public static <E extends CommonResponse> ResponseEntity<E> success(E response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    public static ResponseEntity<CommonResponse> success() {
//        return success(new CommonResponse());
//    }
//
//    public static <T extends CommonResponse, E extends ServiceException> ResponseEntity<T> failure(T response, E ex) {
//        response.setResponseCode(ex.getErrorCode().orElse("ER001"));
//        response.setResponseDescription(ex.getErrorDescription().orElse("Generic Error"));
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> failure(T response) {
//        response.setResponseCode("ER001");
//        response.setResponseDescription("System Error");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> badRequest(T response) {
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> serviceUnavailable(T response, ServiceUnavailableException ex) {
//        response.setResponseCode(ex.getErrorCode().get());
//        response.setResponseDescription(ex.getErrorDescription().get());
//        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> badRequest(T response, BadRequestException ex) {
//        response.setResponseCode(ex.getErrorCode().get());
//        response.setResponseDescription(ex.getErrorDescription().get());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> failureRequest(T response, FailureRequestException ex) {
//        response.setResponseCode(ex.getErrorCode().get());
//        response.setResponseDescription(ex.getErrorDescription().get());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> unauthorized(T response, UnauthorizedException ex) {
//        response.setResponseCode(ex.getErrorCode().get());
//        response.setResponseDescription(ex.getErrorDescription().get());
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    public static <T extends CommonResponse> ResponseEntity<T> systemError(T response, SystemErrorException ex) {
//        response.setResponseCode(ex.getErrorCode().get());
//        response.setResponseDescription(ex.getErrorDescription().get());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
