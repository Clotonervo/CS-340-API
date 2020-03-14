package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.AuthorizationService;
import models.services.UserAliasService;
import net.request.UserAliasRequest;
import net.response.UserAliasResponse;
import services.AuthorizationServiceImpl;
import services.UserAliasServiceImpl;

import java.io.IOException;

public class UserAliasHandler implements RequestHandler<UserAliasRequest, UserAliasResponse> {
    @Override
    public UserAliasResponse handleRequest(UserAliasRequest input, Context context) {
        UserAliasService userAliasService = new UserAliasServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();

        if(input.getAuthToken() == null){
            throw new RuntimeException("[ClientError] Authorization Token not found");
        }

        if(!authorizationService.isValid(input.getAuthToken())){
            throw new RuntimeException("[ClientError] Authorization Token invalid: " + input.getAuthToken());
        }

        try {
            UserAliasResponse response = userAliasService.aliasToUser(input.getUserAlias());
            return response;
        }
        catch (IOException x){
            throw new RuntimeException("[DBError] " + x.getMessage());
        }
    }
}
