package models;

import java.util.List;

public class FeedUpdate {
    public List<User> followers;
    public Status status;

    public FeedUpdate(){}

    public FeedUpdate(List<User> user, Status status){
        this.followers = user;
        this.status = status;
    }

    public List<User> getFollower() {
        return followers;
    }

    public Status getStatus() {
        return status;
    }
}

