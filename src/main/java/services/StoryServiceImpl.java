package services;

import dao.StoryDAO;
import models.services.StoryService;
import net.request.StoryRequest;
import net.response.StoryResponse;

import java.io.IOException;

public class StoryServiceImpl implements StoryService {

    @Override
    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryDAO storyDAO = new StoryDAO();
        return storyDAO.getStory(request);
    }
}
