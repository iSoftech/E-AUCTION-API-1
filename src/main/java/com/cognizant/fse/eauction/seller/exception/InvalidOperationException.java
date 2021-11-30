package com.cognizant.fse.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Operation Exception class to manage invalid operation
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidOperationException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733598L;

    public InvalidOperationException(String message) {
        super(message, HttpStatus.PRECONDITION_FAILED);
    }
}
