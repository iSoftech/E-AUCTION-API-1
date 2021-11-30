package com.cognizant.fse.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Response Entity Exception Handler to handle Exceptions through @ControllerAdvice
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception Handler for Technical Exceptions through {@link TechnicalException} class
     *
     * @param techExc refers to the type {@link TechnicalException}
     * @param webRequest refers to the type {@link WebRequest}
     * @return an instance of {@link ResponseEntity}
     * @throws Exception
     */
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalExceptions(TechnicalException techExc, WebRequest webRequest)
            throws Exception {
        HttpStatus httpStatus = techExc.getHttpStatus() != null ? techExc.getHttpStatus() : HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(),
                techExc.getMessage(), webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}