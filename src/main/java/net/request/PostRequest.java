package net.request;

import models.Status;

public class PostRequest {
    public Status status;
    public String userAlias;
    public String authToken;

    public PostRequest(){}

    public Status getStatus() {
        return status;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserAlias() {
        return userAlias;
    }
}
