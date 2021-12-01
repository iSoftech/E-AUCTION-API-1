package com.cognizant.fse.eauction.seller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Technical Exception class to manage all the technical exceptions
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 6853858869431791704L;

    private HttpStatus status;

    public TechnicalException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public TechnicalException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.status = httpStatus;
    }
}
