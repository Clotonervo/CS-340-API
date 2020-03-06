package net.request;

import models.User;

public class FollowerRequest {

    public User followee;
    public int limit;
    public User lastFollower;

    public FollowerRequest(User followee, int limit, User lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public User getFollower() {
        return followee;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowee() {
        return lastFollower;
    }
}
