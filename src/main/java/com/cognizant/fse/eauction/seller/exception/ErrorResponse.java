package com.cognizant.fse.eauction.seller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Error Response class to manage all the technical exceptions
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer errorCode;
    private String errorStatus;
    private String errorMessage;
    private String errorPath;
    private Date errorOccurredTime;
}
