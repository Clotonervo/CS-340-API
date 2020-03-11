package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.FollowService;
import net.request.FollowRequest;
import net.response.FollowResponse;
import services.FollowServiceImpl;

import java.io.IOException;

public class FollowUserHandler implements RequestHandler<FollowRequest, FollowResponse> {      //TODO: Tests, test that its calling the right methods

    @Override
    public FollowResponse handleRequest(FollowRequest followRequest, Context context) {
        FollowService followService = new FollowServiceImpl();

        if(followRequest.getAuthToken() == null){
            return new FollowResponse("[ClientError]: Authorization Token not found");
        }

        if(!followRequest.getAuthToken().equals("Test")){
            return new FollowResponse("[ClientError]: Authorization Token invalid: " + followRequest.getAuthToken());
        }

        try {
            FollowResponse response = followService.followUser(followRequest.follow);
            return response;
        }
        catch (IOException x){
            return new FollowResponse("[DBError]: " + x.getMessage());
        }
    }
}
