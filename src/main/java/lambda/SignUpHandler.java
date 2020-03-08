package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.SignUpService;
import net.request.SignUpRequest;
import net.response.SignUpResponse;
import services.SignUpServiceImpl;

import java.io.IOException;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {
    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        SignUpService signUpService = new SignUpServiceImpl();
        try{
            SignUpResponse response = signUpService.registerUser(request);
            return response;
        }
        catch(IOException x){
            return new SignUpResponse(x.getMessage());
        }
    }
}
