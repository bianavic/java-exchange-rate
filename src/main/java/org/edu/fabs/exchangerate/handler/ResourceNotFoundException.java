package org.edu.fabs.exchangerate.handler;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id) {
        super("Resource ID not found " + id);
    }

    public HttpStatus getStatus() {
        return status;
    }

}
