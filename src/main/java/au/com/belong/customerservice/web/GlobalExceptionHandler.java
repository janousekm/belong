package au.com.belong.customerservice.web;

import au.com.belong.customerservice.dto.SimpleResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String MESSAGE = "{}: {}";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public SimpleResultDto illegalArgumentException(final IllegalArgumentException e) {
        LOG.warn(MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return new SimpleResultDto(false, e.getMessage());
    }
}
