package net.request;

import models.Status;
import models.User;

public class StoryRequest {

    public User user;
    public int limit;
    public Status lastStatus;


    public StoryRequest(User user, int limit, Status lastStatus){
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public StoryRequest(User user){
        this.user = user;
        this.limit = 0;
    }

    public StoryRequest(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }
}
