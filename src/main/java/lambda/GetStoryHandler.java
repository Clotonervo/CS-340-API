package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.StoryService;
import net.request.StoryRequest;
import net.response.StoryResponse;
import services.AuthorizationServiceImpl;
import services.StoryServiceImpl;

import java.io.IOException;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {

    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        StoryService storyService = new StoryServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();


        if(request.getAuthToken() == null){
            throw new RuntimeException("[ClientError] Authorization Token not found");
        }

        if(!authorizationService.isValid(request.getAuthToken())){
            throw new RuntimeException("[ClientError] Authorization Token invalid: " + request.getAuthToken());
        }

        try {
            StoryResponse response = storyService.getStory(request);
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
