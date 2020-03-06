package dao;

import models.Follow;
import models.User;
import net.request.FollowingRequest;
import net.response.FollowingResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowingDAO {

    private static Map<User, List<User>> followeesByFollower;
    private MockDatabase mockDatabase;

    public FollowingDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public FollowingResponse getFollowees(FollowingRequest request){
        return mockDatabase.getFollowing(request);
    }

}