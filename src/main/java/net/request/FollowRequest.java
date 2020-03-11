package net.request;

import models.Follow;
import net.response.FollowResponse;

public class FollowRequest {
    public Follow follow;
    public String authToken;

    FollowRequest(){}

    public Follow getFollow() {
        return follow;
    }

    public String getAuthToken() {
        return authToken;
    }
}
