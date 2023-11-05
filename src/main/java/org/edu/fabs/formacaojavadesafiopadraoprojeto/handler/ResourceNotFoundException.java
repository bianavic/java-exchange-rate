package org.edu.fabs.formacaojavadesafiopadraoprojeto.handler;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String field) {
        super("resource not found", field);
    }

}
