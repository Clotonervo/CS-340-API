import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lambda.SignOutHandler;
import net.response.SignOutResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SignOutHandlerTest {

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

        SignOutHandler handler = new SignOutHandler();
        SignOutResponse response = handler.handleRequest("@TestUser", context);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Signout complete!", response.getMessage());
    }


    @Test
    public void testInvalidUser(){
        SignOutHandler handler = new SignOutHandler();
        SignOutResponse response = handler.handleRequest("@Invalid", context);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotEquals("Signout complete!", response.getMessage());
    }
}
