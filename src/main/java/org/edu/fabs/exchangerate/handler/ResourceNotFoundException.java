package org.edu.fabs.exchangerate.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id) {
        super("Resource ID not found: " + id);
    }

}
