import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.GetFeedHandler;
import models.User;
import net.request.FeedRequest;
import net.response.FeedResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class GetFeedHandlerTest {

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
        FeedRequest request = new FeedRequest(user, 5, null);
        request.setAuthToken("token");
        GetFeedHandler handler = new GetFeedHandler();
        FeedResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getStatuses());
        Assertions.assertNull(response.getMessage());
        Assertions.assertNotNull(response.getFollowing());
        Assertions.assertEquals(5,response.getStatuses().size());
    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        FeedRequest request = new FeedRequest(user, 5, null);
        request.setAuthToken("Invalid");
        GetFeedHandler handler = new GetFeedHandler();
        handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        User user = new User("Invalid", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        FeedRequest request = new FeedRequest(user, 5, null);
        request.setAuthToken("token");
        GetFeedHandler handler = new GetFeedHandler();
        FeedResponse response = handler.handleRequest(request, context);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("User has no followees!", response.getMessage());
        Assertions.assertNull(response.getStatuses());
    }
}
