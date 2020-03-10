package net.request;

import models.Status;
import models.User;

public class FeedRequest {

    public User user;
    public Status lastStatus;
    public int limit;
    public String authToken;

    public FeedRequest(User user, int limit, Status lastStatus){
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public FeedRequest(User user) {
        this.user = user;
        this.limit = 0;
    }

    public FeedRequest(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getLastStatus()
    {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus)
    {
        this.lastStatus = lastStatus;
    }

    public int getLimit()
    {
        return limit;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String auth) {
        this.authToken = auth;
    }
}
