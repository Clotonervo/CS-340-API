package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.AuthorizationService;
import models.services.FollowService;
import net.request.IsFollowingRequest;
import net.response.IsFollowingResponse;
import services.AuthorizationServiceImpl;
import services.FollowServiceImpl;

import java.io.IOException;

public class IsFollowingHandler implements RequestHandler<IsFollowingRequest, IsFollowingResponse> {
    @Override
    public IsFollowingResponse handleRequest(IsFollowingRequest input, Context context) {
        FollowService followService = new FollowServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            if(input.getAuthToken() == null){
                throw new RuntimeException("[ClientError] Authorization Token not found");
            }

            if(!authorizationService.isValid(input.getAuthToken())){
                throw new RuntimeException("[ClientError] Authorization Token invalid: " + input.getAuthToken());
            }

            IsFollowingResponse response = followService.isFollowing(input.getFollow());
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
