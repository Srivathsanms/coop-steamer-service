package no.coop.task.streamer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception occurred: {}, {}", ex.getMessage(), ex.getClass());
        return new ResponseEntity<>("Error, Contact customer service", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiClientRequestException.class)
    public ResponseEntity<String> handleThirdPartyServiceException(ApiClientRequestException ex) {
        logger.error("Exception occurred in the Third party service connection: {}", ex.getMessage());
        return new ResponseEntity<>("Connection Error, Contact customer service", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
