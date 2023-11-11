package org.edu.fabs.exchangerate.handler;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String field) {
        super("resource not found", field);
    }

}
