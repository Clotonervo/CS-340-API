package net.request;

import models.Status;

public class PostRequest {
    public Status status;
    public String authToken;

    PostRequest(){}

    public Status getStatus() {
        return status;
    }

    public String getAuthToken() {
        return authToken;
    }
}