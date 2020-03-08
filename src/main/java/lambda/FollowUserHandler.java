package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.FollowService;
import net.request.FollowerRequest;
import net.response.FollowResponse;
import services.FollowServiceImpl;

import java.io.IOException;

public class FollowUserHandler implements RequestHandler<Follow, FollowResponse> {

    @Override
    public FollowResponse handleRequest(Follow follow, Context context) {
        FollowService followService = new FollowServiceImpl();
        try {
            FollowResponse response = followService.followUser(follow);
            return response;
        }
        catch (IOException x){
            return new FollowResponse(x.getMessage());
        }
    }
}
