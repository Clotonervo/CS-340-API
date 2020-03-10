package models.services;

import models.Follow;
import net.response.FollowResponse;
import net.response.IsFollowingResponse;

import java.io.IOException;

public interface FollowService {
    FollowResponse followUser(Follow follow) throws IOException;
    IsFollowingResponse isFollowing(Follow follow) throws IOException;
}
