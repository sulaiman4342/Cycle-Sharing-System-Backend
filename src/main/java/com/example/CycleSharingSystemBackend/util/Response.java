package com.example.CycleSharingSystemBackend.util;
public class Response {

    private boolean success;
    private String message;

    public Response() {
        this.success = true;
    }

    public Response(boolean success, String message) {
        this.success = success;
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

    @Override
    public String toString() {
        return "Response{" + "success=" + success + ", message='" + message + '\'' + '}';
    }
}
