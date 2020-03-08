package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Follow;
import models.services.UnfollowService;
import net.response.UnfollowResponse;
import services.UnfollowServiceImpl;

import java.io.IOException;

public class UnfollowUserHandler implements RequestHandler<Follow, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(Follow follow, Context context) {
        UnfollowService unfollowService = new UnfollowServiceImpl();
        try {
            UnfollowResponse unfollowResponse = unfollowService.unfollowUser(follow);
            return unfollowResponse;
        }
        catch (IOException x){
            return new UnfollowResponse(x.getMessage());
        }
    }
}
