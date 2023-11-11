package org.edu.fabs.exchangerate.handler;

import lombok.Setter;

import java.util.Date;

@Setter
public class ResponseError {

    private Date timestamp = new Date();
    private String status = "error";
    private int statusCode = 400;
    private String error;

}
