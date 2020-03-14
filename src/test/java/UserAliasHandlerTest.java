import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.UserAliasHandler;
import models.User;
import net.request.UserAliasRequest;
import net.response.UserAliasResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UserAliasHandlerTest {

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
        UserAliasRequest request = new UserAliasRequest();
        request.authToken = "token";
        request.userAlias = "@TestUser";

        UserAliasHandler handler = new UserAliasHandler();
        UserAliasResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
        Assertions.assertEquals(user, response.getUser());

    }

    @Test (expected = RuntimeException.class)
    public void testInvalidAuthToken(){
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        UserAliasRequest request = new UserAliasRequest();
        request.authToken = "Invalid";
        request.userAlias = "@TestUser";

        UserAliasHandler handler = new UserAliasHandler();
        handler.handleRequest(request, context);
    }

    @Test
    public void testInvalidUser(){
        UserAliasRequest request = new UserAliasRequest();
        request.authToken = "token";
        request.userAlias = "@Invalid";

        UserAliasHandler handler = new UserAliasHandler();
        UserAliasResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertNull(response.getUser());
    }
}
