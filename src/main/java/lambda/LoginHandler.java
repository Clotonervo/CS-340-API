package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.LoginService;
import net.request.LoginRequest;
import net.response.LoginResponse;
import services.AuthorizationServiceImpl;
import services.LoginServiceImpl;

import java.io.IOException;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {
        LoginService loginService = new LoginServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        try {
            LoginResponse response = loginService.login(request);
            if(response.isSuccess()) {
                response.setAuthToken(authorizationService.generateAuthToken(response.getUser().getAlias()));
            }
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
