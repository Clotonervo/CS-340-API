package net.response;

import models.User;

import java.util.List;


public class FollowerResponse extends PagedResponse {

    private List<User> followers;

    public FollowerResponse(String message) {
        super(false, message, false);
    }

    public FollowerResponse(List<User> followers, boolean hasMorePages) {
        super(true, hasMorePages);
        this.followers = followers;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public boolean isSuccess() {
        return super.isSuccess();
    }
}