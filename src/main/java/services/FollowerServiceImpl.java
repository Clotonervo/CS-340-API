package services;

import dao.FollowDAO;
import models.services.FollowerService;
import net.request.FollowerRequest;
import net.response.FollowerResponse;

import java.io.IOException;


public class FollowerServiceImpl implements FollowerService {

    @Override
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        FollowDAO followDAO = new FollowDAO();
        return followDAO.getFollowers(request);
    }
}
