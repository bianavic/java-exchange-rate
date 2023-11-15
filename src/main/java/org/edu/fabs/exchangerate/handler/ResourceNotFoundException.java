package org.edu.fabs.exchangerate.handler;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id) {
        super("Resource ID not found " + id);
    }

}
