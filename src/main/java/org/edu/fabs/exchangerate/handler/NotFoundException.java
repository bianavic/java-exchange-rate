package org.edu.fabs.exchangerate.handler;

public class NotFoundException extends BusinessException {

    public NotFoundException(String mensagem) {
        super("Resource not found.");
    }

}
