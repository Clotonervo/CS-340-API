package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.FollowService;
import net.request.IsFollowingRequest;
import net.response.IsFollowingResponse;
import services.FollowServiceImpl;

import java.io.IOException;

public class IsFollowingHandler implements RequestHandler<IsFollowingRequest, IsFollowingResponse> {
    @Override
    public IsFollowingResponse handleRequest(IsFollowingRequest input, Context context) {
        FollowService followService = new FollowServiceImpl();

        if(input.getAuthToken() == null){
            return new IsFollowingResponse("[ClientError]: Authorization Token not found");
        }

        if(!input.getAuthToken().equals("Test")){
            return new IsFollowingResponse("[ClientError]: Authorization Token invalid: " + input.getAuthToken());
        }

        try {
            IsFollowingResponse response = followService.isFollowing(input.getFollow());
            return response;
        }
        catch (IOException x){
            return new IsFollowingResponse("[DBError]: " + x.getMessage());
        }
    }
}
