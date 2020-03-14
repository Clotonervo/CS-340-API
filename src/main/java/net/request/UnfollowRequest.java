package net.request;

import models.Follow;


public class UnfollowRequest {
    public Follow follow;
    public String authToken;

    public UnfollowRequest(){}

    public UnfollowRequest(Follow follow, String authToken) {
        this.follow = follow;
        this.authToken = authToken;
    }

    public Follow getFollow() {
        return follow;
    }

    public String getAuthToken() {
        return authToken;
    }
}
