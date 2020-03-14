import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.LoginHandler;
import net.request.LoginRequest;
import net.response.LoginResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LoginHandlerTest {

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

        LoginRequest request = new LoginRequest("@TestUser", "password");
        LoginHandler handler = new LoginHandler();
        LoginResponse response = handler.handleRequest(request, context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getUser());
        Assertions.assertEquals("token", response.getAuthToken());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void testInvalidUser(){
        LoginRequest request = new LoginRequest("@Invalid", "incorrect");
        LoginHandler handler = new LoginHandler();
        LoginResponse response = handler.handleRequest(request, context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getUser());
        Assertions.assertNull(response.getAuthToken());
        Assertions.assertNotNull(response.getMessage());
    }
}
