import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.PostHandler;
import models.Status;
import models.User;
import net.request.PostRequest;
import net.response.PostResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PostHandlerTest {

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
        PostRequest request = new PostRequest();
        Status status = new Status(new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), "new status update!");
        request.authToken = "token";
        request.status = status;
        PostHandler handler = new PostHandler();
        PostResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Successfully posted!", response.getMessage());
    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        PostRequest request = new PostRequest();
        Status status = new Status(new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), "new status update!");
        request.authToken = "Invalid";
        request.status = status;
        PostHandler handler = new PostHandler();
        handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        PostRequest request = new PostRequest();
        Status status = new Status(new User("Invalid", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), "new status update!");
        request.authToken = "token";
        request.status = status;
        PostHandler handler = new PostHandler();
        PostResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }
}
