package net.response;

import models.User;

import java.util.List;


public class FollowingResponse extends PagedResponse {

    private List<User> followees;
    private boolean hasMorePages;

    public FollowingResponse(String message) {
        super(false, message, false);
    }

    public FollowingResponse(List<User> followees, boolean hasMorePages) {
        super(true, hasMorePages);
        this.hasMorePages = hasMorePages;                                       //TODO: Figure out why this is not returning
        this.followees = followees;
    }

    public List<User> getFollowees() {
        return followees;
    }

    public boolean isSuccess(){
        return super.isSuccess();
    }
}
