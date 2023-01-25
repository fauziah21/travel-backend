package com.fauzia.project.backend.response;

public class SuccessResponse<T> {
    private boolean status;
    private T message;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
