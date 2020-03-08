package services;

import dao.FollowDAO;
import models.Follow;
import models.services.UnfollowService;
import net.response.UnfollowResponse;

import java.io.IOException;

public class UnfollowServiceImpl implements UnfollowService {
    @Override
    public UnfollowResponse unfollowUser(Follow follow) throws IOException {
        FollowDAO followDAO = new FollowDAO();
        return followDAO.unfollowUser(follow);
    }
}
