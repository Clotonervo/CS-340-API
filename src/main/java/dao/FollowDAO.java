package dao;

import models.Follow;
import models.services.UnfollowService;
import net.response.FollowResponse;
import net.response.UnfollowResponse;

public class FollowDAO {

    private MockDatabase mockDatabase;

    public FollowDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public FollowResponse followUser(Follow follow){
        return mockDatabase.followUser(follow);
    }

    public UnfollowResponse unfollowUser(Follow follow) {
        return mockDatabase.unfollowUser(follow);
    }
}
