package com.fauzia.project.backend.response;

public class ErrorResponse<T> {
    private boolean status;
    private T errorCode;
    private T message;




    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(T errorCode) {
        this.errorCode = errorCode;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
