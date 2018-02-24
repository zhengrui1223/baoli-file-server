/*
 * Copyright (C) 2014 SEC BFO, Inc. All Rights Reserved.
 */
package com.baoli.exception;

/**
 * Base exception
 *
 * @author Spring
 */
public class BLBaseException extends Exception {

    private static final long serialVersionUID = 4374039979963462421L;

    protected String errorCode;
    protected String errorMsg;
    protected Object[] args;

    public BLBaseException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BLBaseException(String errorCode, String errorMsg, Object[] args) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.args = args;
    }

    public BLBaseException(String errorCode, String errorMsg, Throwable t) {
        super(errorMsg, t);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BLBaseException(String errorCode, String errorMsg, Object[] args, Throwable t) {
        super(errorMsg, t);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.args = args;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
