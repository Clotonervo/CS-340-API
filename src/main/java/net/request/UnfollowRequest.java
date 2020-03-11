package net.request;

import models.Follow;


public class UnfollowRequest {
    public Follow follow;
    public String authToken;

    UnfollowRequest(){}

    public Follow getFollow() {
        return follow;
    }

    public String getAuthToken() {
        return authToken;
    }
}
