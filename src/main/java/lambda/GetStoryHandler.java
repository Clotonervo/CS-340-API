package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.StoryService;
import net.request.StoryRequest;
import net.response.StoryResponse;
import services.StoryServiceImpl;

import java.io.IOException;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {

    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        StoryService storyService = new StoryServiceImpl();
        try {
            StoryResponse response = storyService.getStory(request);
            return response;
        }
        catch (IOException x){
            return new StoryResponse(x.getMessage());
        }
    }
}
