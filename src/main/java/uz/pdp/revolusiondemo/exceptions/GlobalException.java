package uz.pdp.revolusiondemo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
