package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.request.FollowerRequest;
import net.response.FollowerResponse;
import services.FollowerServiceImpl;

import java.io.IOException;

public class GetFollowerHandler implements RequestHandler<FollowerRequest, FollowerResponse> {

    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        FollowerServiceImpl service = new FollowerServiceImpl();
        try {
            FollowerResponse response = service.getFollowers(request);
            return response;
        }
        catch (IOException x){
            return new FollowerResponse(x.getMessage());
        }
    }
}
