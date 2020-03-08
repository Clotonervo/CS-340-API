package models.services;

import models.Follow;
import net.response.UnfollowResponse;

import java.io.IOException;

public interface UnfollowService {
    UnfollowResponse unfollowUser(Follow follow) throws IOException;

}
