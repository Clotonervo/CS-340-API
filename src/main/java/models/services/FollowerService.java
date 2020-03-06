package models.services;

import net.request.FollowerRequest;
import net.response.FollowerResponse;

import java.io.IOException;

public interface FollowerService {
    FollowerResponse getFollowers(FollowerRequest request) throws IOException;
}
