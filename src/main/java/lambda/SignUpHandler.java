package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.SignUpService;
import net.request.SignUpRequest;
import net.response.SignUpResponse;
import services.AuthorizationServiceImpl;
import services.SignUpServiceImpl;

import java.io.IOException;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {
    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        SignUpService signUpService = new SignUpServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try{
            SignUpResponse response = signUpService.registerUser(request);
            if(response.isSuccess()) {
                response.setAuthToken(authorizationService.generateAuthToken());
            }
            return response;
        }
        catch(IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
