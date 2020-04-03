package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.FollowService;
import net.request.FollowRequest;
import net.response.FollowResponse;
import services.AuthorizationServiceImpl;
import services.FollowServiceImpl;

import java.io.IOException;

public class FollowUserHandler implements RequestHandler<FollowRequest, FollowResponse> {      //TODO: Tests, test that its calling the right methods

    @Override
    public FollowResponse handleRequest(FollowRequest followRequest, Context context) {
        FollowService followService = new FollowServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            if(followRequest.getAuthToken() == null){
                throw new RuntimeException("[ClientError] Authorization Token not found");
            }

            if(!authorizationService.isValid(followRequest.getAuthToken())){
                throw new RuntimeException("[ClientError] Authorization Token invalid: " + followRequest.getAuthToken());
            }

            FollowResponse response = followService.followUser(followRequest.follow);
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
