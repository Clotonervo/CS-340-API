package net.request;

import models.Follow;

public class IsFollowingRequest {
    public Follow follow;
    public String authToken;

    IsFollowingRequest(){}

    public Follow getFollow() {
        return follow;
    }

    public String getAuthToken() {
        return authToken;
    }
}
