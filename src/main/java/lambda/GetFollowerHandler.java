package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.request.FollowerRequest;
import net.response.FollowerResponse;
import services.FollowerServiceImpl;

public class GetFollowerHandler implements RequestHandler<FollowerRequest, FollowerResponse> {

    @Override
    public FollowerResponse handleRequest(FollowerRequest request, Context context) {
        FollowerServiceImpl service = new FollowerServiceImpl();
        return service.getFollowers(request);
    }
}
