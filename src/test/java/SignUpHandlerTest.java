import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.SignUpHandler;
import models.User;
import net.request.SignUpRequest;
import net.response.SignUpResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SignUpHandlerTest {

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
        User user = new User("Johnny", "Cash", "Username", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        SignUpRequest request = new SignUpRequest("Username", "password","Johnny", "Cash", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        SignUpHandler handler = new SignUpHandler();
        SignUpResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(user, response.getSignedInUser());
        Assertions.assertEquals("token", response.getAuthToken());
        Assertions.assertNull(response.getMessage());

    }

    @Test
    public void testInvalidUser(){
        SignUpRequest request = new SignUpRequest("@TestUser","password","Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        SignUpHandler handler = new SignUpHandler();
        SignUpResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getSignedInUser());
        Assertions.assertNull(response.getAuthToken());
        Assertions.assertNotNull(response.getMessage());
    }
}
