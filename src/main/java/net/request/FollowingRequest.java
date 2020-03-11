package net.request;


public class FollowingRequest {

    public String follower;
    public int limit;
    public String lastFollowee;
    public String authToken;


    public FollowingRequest(String follower, int limit, String lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public FollowingRequest(){}

    public String getFollower() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollowee() {
        return lastFollowee;
    }

    public String getAuthToken() {
        return authToken;
    }
}
