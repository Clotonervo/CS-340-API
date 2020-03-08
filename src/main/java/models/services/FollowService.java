package models.services;

import models.Follow;
import net.response.FollowResponse;

import java.io.IOException;

public interface FollowService {
    FollowResponse followUser(Follow follow) throws IOException;
}
