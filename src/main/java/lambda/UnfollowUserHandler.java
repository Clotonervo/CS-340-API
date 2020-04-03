package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.UnfollowService;
import net.request.UnfollowRequest;
import net.response.UnfollowResponse;
import services.AuthorizationServiceImpl;
import services.UnfollowServiceImpl;

import java.io.IOException;

public class UnfollowUserHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest unfollowRequest, Context context) {
        UnfollowService unfollowService = new UnfollowServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            if(unfollowRequest.getAuthToken() == null){
                throw new RuntimeException("[ClientError] Authorization Token not found");
            }

            if(!authorizationService.isValid(unfollowRequest.getAuthToken())){
                throw new RuntimeException("[ClientError] Authorization Token invalid: " + unfollowRequest.getAuthToken());
            }

            UnfollowResponse unfollowResponse = unfollowService.unfollowUser(unfollowRequest.getFollow());
            return unfollowResponse;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
