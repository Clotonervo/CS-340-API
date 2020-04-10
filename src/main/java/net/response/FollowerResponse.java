package net.response;

import models.User;

import java.util.List;


public class FollowerResponse extends PagedResponse {

    private List<User> followers;
    private String lastFollower;

    public FollowerResponse(String message) {
        super(false, message, false);
    }

    public FollowerResponse(List<User> followers, boolean hasMorePages, String lastFollower) {
        super(true, hasMorePages);
        this.followers = followers;
        this.lastFollower = lastFollower;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public boolean isSuccess() {
        return super.isSuccess();
    }

    public String getLastFollower(){
        return this.lastFollower;
    }
}