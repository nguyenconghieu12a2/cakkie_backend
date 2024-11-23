package com.cakkie.backend.exception.adminCustomers;

import com.cakkie.backend.exception.adminBanners.BannerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BannerNotFoundException.class)
    public Map<String, String> bannersNotFound(BannerNotFoundException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(CustomerNotFoundException.class)
//    public Map<String, String> customerNotFound(CustomerNotFoundException ex){
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return error;
//    }



//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(CustomerAlreadyExistsException.class)
//    public Map<String, String> customerNotFound(CustomerAlreadyExistsException ex){
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return error;
//    }

}
