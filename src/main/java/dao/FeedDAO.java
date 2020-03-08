package dao;

import net.request.FeedRequest;
import net.response.FeedResponse;
import net.response.FollowerResponse;

public class FeedDAO {

    private MockDatabase mockDatabase;

    public FeedDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public FeedResponse getFeed(FeedRequest request){
        return mockDatabase.getFeed(request);
    }
}
