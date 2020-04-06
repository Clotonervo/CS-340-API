package services;

import dao.FollowDAO;
import models.services.FollowingService;
import net.request.FollowingRequest;
import net.response.FollowingResponse;

import java.io.IOException;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        FollowDAO dao = new FollowDAO();
        return dao.getFollowees(request);
    }
}
