package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import net.request.FeedRequest;
import net.response.FeedResponse;
import services.AuthorizationServiceImpl;
import services.FeedServiceImpl;

import java.io.IOException;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {  //TODO: Do tests!

    public FeedResponse handleRequest(FeedRequest request, Context context) {
        FeedServiceImpl feedService = new FeedServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            if(request.getAuthToken() == null){
                throw new RuntimeException("[ClientError] Authorization Token not found");
            }

            if(!authorizationService.isValid(request.getAuthToken())){
                throw new RuntimeException("[ClientError] Authorization Token invalid: " + request.getAuthToken());
            }

            FeedResponse response = feedService.getFeed(request);
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }

}
