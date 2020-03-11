package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.UserAliasService;
import net.request.UserAliasRequest;
import net.response.UserAliasResponse;
import services.UserAliasServiceImpl;

import java.io.IOException;

public class UserAliasHandler implements RequestHandler<UserAliasRequest, UserAliasResponse> {
    @Override
    public UserAliasResponse handleRequest(UserAliasRequest input, Context context) {
        UserAliasService userAliasService = new UserAliasServiceImpl();

        if(input.getAuthToken() == null){
            return new UserAliasResponse("[ClientError]: Authorization Token not found");
        }

        if(!input.getAuthToken().equals("Test")){
            return new UserAliasResponse("[ClientError]: Authorization Token invalid: " + input.getAuthToken());
        }

        try {
            UserAliasResponse response = userAliasService.aliasToUser(input.getUserAlias());
            return response;
        }
        catch (IOException x){
            return new UserAliasResponse("[DBError]: " + x.getMessage());
        }
    }
}
