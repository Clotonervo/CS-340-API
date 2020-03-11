package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.UnfollowService;
import net.request.UnfollowRequest;
import net.response.UnfollowResponse;
import services.UnfollowServiceImpl;

import java.io.IOException;

public class UnfollowUserHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest unfollowRequest, Context context) {
        UnfollowService unfollowService = new UnfollowServiceImpl();

        if(unfollowRequest.getAuthToken() == null){
            return new UnfollowResponse("[ClientError]: Authorization Token not found");
        }

        if(!unfollowRequest.getAuthToken().equals("Test")){
            return new UnfollowResponse("[ClientError]: Authorization Token invalid: " + unfollowRequest.getAuthToken());
        }

        try {
            UnfollowResponse unfollowResponse = unfollowService.unfollowUser(unfollowRequest.getFollow());
            return unfollowResponse;
        }
        catch (IOException x){
            return new UnfollowResponse("[DBError]: " + x.getMessage());
        }
    }
}
