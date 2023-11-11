package org.edu.fabs.exchangerate.handler;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String mensagem) {
        super(mensagem);
    }


}
