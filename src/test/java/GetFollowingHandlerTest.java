import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.GetFollowingHandler;
import net.request.FollowingRequest;
import net.response.FollowingResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GetFollowingHandlerTest {

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

        FollowingRequest request = new FollowingRequest("@TestUser", 5, null);
        GetFollowingHandler handler = new GetFollowingHandler();
        request.authToken = "token";
        FollowingResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(5, response.getFollowees().size());
        Assertions.assertNull(response.getMessage());

    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){

        FollowingRequest request = new FollowingRequest("@TestUser", 5, null);
        GetFollowingHandler handler = new GetFollowingHandler();
        request.authToken = "Invalid";
        handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        FollowingRequest request = new FollowingRequest("@Invalid", 5, null);
        GetFollowingHandler handler = new GetFollowingHandler();
        request.authToken = "token";
        FollowingResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getFollowees());
        Assertions.assertNotNull(response.getMessage());
    }
}
