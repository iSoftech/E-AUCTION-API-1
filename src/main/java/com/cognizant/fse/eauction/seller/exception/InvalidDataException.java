package com.cognizant.fse.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Data Exception class to manage invalid data
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733598L;

    public InvalidDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
