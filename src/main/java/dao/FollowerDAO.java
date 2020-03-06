package dao;

import net.request.FollowerRequest;
import net.response.FollowerResponse;

public class FollowerDAO {

    private MockDatabase mockDatabase;

    public FollowerDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public FollowerResponse getFollowers(FollowerRequest request){
        return mockDatabase.getFollowers(request);
    }
}
