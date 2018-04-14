package io.github.javathought.devoxx.resources.utils;

/**
 * Message send as simple REST message
 */
public class ResponseMessage {
    private boolean success;
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(boolean succes, String message) {
        this.success = succes;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
