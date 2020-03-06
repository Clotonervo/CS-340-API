package services;

import dao.FollowingDAO;
import models.services.FollowingService;
import net.request.FollowingRequest;
import net.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        FollowingDAO dao = new FollowingDAO();
        return dao.getFollowees(request);
    }
}
