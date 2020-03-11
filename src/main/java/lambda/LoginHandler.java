package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.LoginService;
import net.request.LoginRequest;
import net.response.LoginResponse;
import services.LoginServiceImpl;

import java.io.IOException;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {     //TODO: Authtokens
        LoginService loginService = new LoginServiceImpl();
        try {
            LoginResponse response = loginService.login(request);
            response.setAuthToken("Test");
            return response;
        }
        catch (IOException x){
            return new LoginResponse("[DBError]: " + x.getMessage());
        }
    }
}
