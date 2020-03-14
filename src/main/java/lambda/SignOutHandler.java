package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.SignOutService;
import net.response.SignOutResponse;
import services.AuthorizationServiceImpl;
import services.SignOutServiceImpl;

import java.io.IOException;

public class SignOutHandler implements RequestHandler<String, SignOutResponse> {

    @Override
    public SignOutResponse handleRequest(String input, Context context) {
        SignOutService signOutService = new SignOutServiceImpl();

        try {
            SignOutResponse response = signOutService.signOut(input);
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
