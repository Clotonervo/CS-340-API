package models.services;

import net.request.StoryRequest;
import net.response.StoryResponse;

import java.io.IOException;

public interface StoryService {
    StoryResponse getStory(StoryRequest request) throws IOException;
}
