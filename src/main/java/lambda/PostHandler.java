package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.PostService;
import net.request.PostRequest;
import net.response.PostResponse;
import services.PostServiceImpl;

import java.io.IOException;

public class PostHandler implements RequestHandler<PostRequest, PostResponse> {
    @Override
    public PostResponse handleRequest(PostRequest input, Context context) {
        PostService postService = new PostServiceImpl();

        if(input.getAuthToken() == null){
            return new PostResponse(false, "[ClientError]: Authorization Token not found");
        }

        if(!input.getAuthToken().equals("Test")){
            return new PostResponse(false, "[ClientError]: Authorization Token invalid: " + input.getAuthToken());
        }

        try {
            PostResponse response = postService.postStatus(input.getStatus());
            return response;
        }
        catch (IOException x){
            return new PostResponse(false, "[DBError]: " + x.getMessage());
        }
    }
}
