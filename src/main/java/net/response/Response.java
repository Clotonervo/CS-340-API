package net.response;

class Response {

    public final boolean success;
    public final String message;

    Response(boolean success) {
        this(success, null);
    }

    Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
