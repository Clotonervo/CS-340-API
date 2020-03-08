package services;

import dao.FollowerDAO;
import models.services.FollowerService;
import net.request.FollowerRequest;
import net.response.FollowerResponse;

import java.io.IOException;


public class FollowerServiceImpl implements FollowerService {

    @Override
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        FollowerDAO followerDAO = new FollowerDAO();
        return followerDAO.getFollowers(request);
    }
}
