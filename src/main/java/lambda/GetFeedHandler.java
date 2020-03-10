package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.request.FeedRequest;
import net.response.FeedResponse;
import services.FeedServiceImpl;

import java.io.IOException;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    public FeedResponse handleRequest(FeedRequest request, Context context) {
        FeedServiceImpl feedService = new FeedServiceImpl();
        try {
            FeedResponse response = feedService.getFeed(request);
            return response;
        }
        catch (IOException x){
            return new FeedResponse(x.getMessage());
        }
    }

}
