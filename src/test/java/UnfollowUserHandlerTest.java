import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.UnfollowUserHandler;
import models.Follow;
import models.User;
import net.request.UnfollowRequest;
import net.response.UnfollowResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UnfollowUserHandlerTest {

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
        User follower = new User("AA", "A", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User followee = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Follow follow = new Follow(follower, followee);
        UnfollowRequest request = new UnfollowRequest(follow, "token");
        UnfollowUserHandler handler = new UnfollowUserHandler();
        UnfollowResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        User follower = new User("AA", "A", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User followee = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Follow follow = new Follow(follower, followee);
        UnfollowRequest request = new UnfollowRequest(follow, "Invalid");
        UnfollowUserHandler handler = new UnfollowUserHandler();
        UnfollowResponse response = handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        User follower = new User("Invalid", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User followee = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Follow follow = new Follow(follower, followee);
        UnfollowRequest request = new UnfollowRequest(follow, "token");
        UnfollowUserHandler handler = new UnfollowUserHandler();
        UnfollowResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }
}
