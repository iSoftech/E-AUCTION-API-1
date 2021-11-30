package com.cognizant.fse.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource Exist Exception class to manage all the resource that are already exist exceptions
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistException extends TechnicalException {

    private static final long serialVersionUID = -7206259820819733712L;

    public ResourceExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
