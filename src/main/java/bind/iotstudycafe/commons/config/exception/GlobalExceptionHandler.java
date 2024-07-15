package bind.iotstudycafe.commons.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
//        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
//    }
//
//    @ExceptionHandler(WebClientResponseException.class)
//    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
//        if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
//            return new ResponseEntity<>("Bad Request from External API", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
