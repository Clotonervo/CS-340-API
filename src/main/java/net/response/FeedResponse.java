package net.response;


import models.Status;
import models.User;

import java.util.List;

public class FeedResponse extends PagedResponse  {
    private List<Status> statuses;
    private List<User> following;

    public FeedResponse(String message){
        super(false, message, false);
    }

    public FeedResponse(boolean hasMorePages, List<Status> statuses, List<User> users)
    {
        super(true, null, hasMorePages);
        this.statuses = statuses;
        this.following = users;
    }

    public List<Status> getStatuses()
    {
        return statuses;
    }

    public void setStatuses(List<Status> statuses)
    {
        this.statuses = statuses;
    }

    public List<User> getFollowing() {
        return following;
    }

    public boolean isSuccess() {
        return super.isSuccess();
    }
}
