package dao;

import net.request.FeedRequest;
import net.response.FeedResponse;
import net.response.FollowerResponse;

import java.io.IOException;

public class FeedDAO {

    private MockDatabase mockDatabase;

    public FeedDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        return mockDatabase.getFeed(request);
    }
}
