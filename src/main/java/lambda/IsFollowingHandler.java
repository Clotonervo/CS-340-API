package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.FollowService;
import net.response.IsFollowingResponse;
import services.FollowServiceImpl;

import java.io.IOException;

public class IsFollowingHandler implements RequestHandler<Follow, IsFollowingResponse> {
    @Override
    public IsFollowingResponse handleRequest(Follow input, Context context) {
        FollowService followService = new FollowServiceImpl();
        try {
            IsFollowingResponse response = followService.isFollowing(input);
            return response;
        }
        catch (IOException x){
            return new IsFollowingResponse(x.getMessage());
        }
    }
}
