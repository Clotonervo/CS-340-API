package net.request;

import models.Follow;
import net.response.FollowResponse;

public class FollowRequest {
    public Follow follow;
    public String authToken;

    public FollowRequest(){}

    public FollowRequest(Follow follow, String authToken) {
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
