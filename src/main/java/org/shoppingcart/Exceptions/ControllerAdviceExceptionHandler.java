package org.shoppingcart.Exceptions;

import org.shoppingcart.Model.Response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<APIResponse> RuntimeExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse(exception.getMessage(), null));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse("NOT FOUND", exception.getMessage()));
    }
}
