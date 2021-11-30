package com.cognizant.fse.eauction.seller.controller;

import com.cognizant.fse.eauction.seller.common.RestApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Health Controller to check service health status
 *
 * @author Mohamed Yusuff
 * @since 20/11/2021
 */
@Api(tags = "Health Check REST Controller")
@RestApiController("health")
@RequiredArgsConstructor
public class HealthController {

    @ApiOperation(value = "Returns the service node status", response = ResponseEntity.class, notes = "The end-point returns the node status of the service.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    @GetMapping(value = "/node-status")
    @ResponseBody
    public ResponseEntity<HttpStatus> nodeStatus() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
