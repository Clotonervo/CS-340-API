import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.GetFollowerHandler;
import net.request.FollowerRequest;
import net.response.FollowerResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GetFollowerHandlerTest {

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

        FollowerRequest request = new FollowerRequest("@TestUser", 5, null);
        request.authToken = "token";
        GetFollowerHandler handler = new GetFollowerHandler();
        FollowerResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getFollowers());
        Assertions.assertNull(response.getMessage());
        Assertions.assertEquals(5, response.getFollowers().size());
    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        FollowerRequest request = new FollowerRequest("@TestUser", 5, null);
        request.authToken = "Invalid";
        GetFollowerHandler handler = new GetFollowerHandler();
        handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        FollowerRequest request = new FollowerRequest("@Invalid", 5, null);
        request.authToken = "token";
        GetFollowerHandler handler = new GetFollowerHandler();
        FollowerResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }
}
