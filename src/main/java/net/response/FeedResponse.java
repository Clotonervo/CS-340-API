package net.response;


import models.Status;
import models.User;

import java.util.List;

public class FeedResponse extends PagedResponse  {
    private List<Status> statuses;

    public FeedResponse(String message){
        super(false, message, false);
    }

    public FeedResponse(boolean hasMorePages, List<Status> statuses)
    {
        super(true, null, hasMorePages);
        this.statuses = statuses;
    }

    public List<Status> getStatuses()
    {
        return statuses;
    }

    public void setStatuses(List<Status> statuses)
    {
        this.statuses = statuses;
    }

    public boolean isSuccess() {
        return super.isSuccess();
    }
}
