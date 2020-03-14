import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.GetStoryHandler;
import models.User;
import net.request.StoryRequest;
import net.response.StoryResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GetStoryHandlerTest {

    Context context = new Context() {
        @Override
        public String getAwsRequestId() {
            return null;
        }

        @Override
        public String getLogGroupName() {
            return null;
        }

        @Override
        public String getLogStreamName() {
            return null;
        }

        @Override
        public String getFunctionName() {
            return null;
        }

        @Override
        public String getFunctionVersion() {
            return null;
        }

        @Override
        public String getInvokedFunctionArn() {
            return null;
        }

        @Override
        public CognitoIdentity getIdentity() {
            return null;
        }

        @Override
        public ClientContext getClientContext() {
            return null;
        }

        @Override
        public int getRemainingTimeInMillis() {
            return 0;
        }

        @Override
        public int getMemoryLimitInMB() {
            return 0;
        }

        @Override
        public LambdaLogger getLogger() {
            return null;
        }
    };

    @Test
    public void testBasicFunctionality(){
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        StoryRequest request = new StoryRequest(user, 5, null);
        GetStoryHandler handler = new GetStoryHandler();
        request.authToken = "token";
        StoryResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getStatusList());
        Assertions.assertNull(response.getMessage());
        Assertions.assertEquals(5,response.getStatusList().size());
    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        StoryRequest request = new StoryRequest(user, 5, null);
        GetStoryHandler handler = new GetStoryHandler();
        request.authToken = "Invalid";
        StoryResponse response = handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        User user = new User("Invalid", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        StoryRequest request = new StoryRequest(user, 5, null);
        GetStoryHandler handler = new GetStoryHandler();
        request.authToken = "token";
        StoryResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNull(response.getStatusList());
    }
}
