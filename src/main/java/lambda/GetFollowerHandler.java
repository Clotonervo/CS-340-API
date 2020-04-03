package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import net.request.FollowerRequest;
import net.response.FollowerResponse;
import services.AuthorizationServiceImpl;
import services.FollowerServiceImpl;

import java.io.IOException;

public class GetFollowerHandler implements RequestHandler<FollowerRequest, FollowerResponse> {

    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        FollowerServiceImpl service = new FollowerServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            if(request.getAuthToken() == null){
                throw new RuntimeException("[ClientError] Authorization Token not found");
            }

            if(!authorizationService.isValid(request.getAuthToken())){
                throw new RuntimeException("[ClientError] Authorization Token invalid: " + request.getAuthToken());
            }

            FollowerResponse response = service.getFollowers(request);
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
