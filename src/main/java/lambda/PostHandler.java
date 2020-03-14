package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.PostService;
import net.request.PostRequest;
import net.response.PostResponse;
import services.AuthorizationServiceImpl;
import services.PostServiceImpl;

import java.io.IOException;

public class PostHandler implements RequestHandler<PostRequest, PostResponse> {
    @Override
    public PostResponse handleRequest(PostRequest input, Context context) {
        PostService postService = new PostServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();
        if(input.getAuthToken() == null){
            throw new RuntimeException("[ClientError] Authorization Token not found");
        }

        if(!authorizationService.isValid(input.getAuthToken())){
            throw new RuntimeException("[ClientError] Authorization Token invalid: " + input.getAuthToken());
        }

        try {
            PostResponse response = postService.postStatus(input.getStatus());
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
