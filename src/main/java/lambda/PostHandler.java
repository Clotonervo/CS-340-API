package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.Status;
import models.services.PostService;
import net.response.PostResponse;
import services.PostServiceImpl;

import java.io.IOException;

public class PostHandler implements RequestHandler<Status, PostResponse> {
    @Override
    public PostResponse handleRequest(Status input, Context context) {
        PostService postService = new PostServiceImpl();
        try {
            PostResponse response = postService.postStatus(input);
            return response;
        }
        catch (IOException x){
            return new PostResponse(false, x.getMessage());
        }
    }
}
