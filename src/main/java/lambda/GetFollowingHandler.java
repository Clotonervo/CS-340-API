package lambda;

import net.request.FollowingRequest;
import net.response.FollowingResponse;
import services.FollowingServiceImpl;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetFollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        try {
            FollowingResponse response = service.getFollowees(request);
            return response;
        }
        catch (IOException x){
            return new FollowingResponse(x.getMessage());           //TODO: Put [DBError] thing in all services
        }
    }
}
