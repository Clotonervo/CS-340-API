package services;

import dao.FeedDAO;
import models.services.FeedService;
import net.request.FeedRequest;
import net.response.FeedResponse;

import java.io.IOException;

public class FeedServiceImpl implements FeedService {

    @Override
    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedDAO feedDAO = new FeedDAO();
        return feedDAO.getFeed(request);
    }
}
