package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import models.services.UserAliasService;
import net.response.UserAliasResponse;
import services.UserAliasServiceImpl;

import java.io.IOException;

public class UserAliasHandler implements RequestHandler<String, UserAliasResponse> {
    @Override
    public UserAliasResponse handleRequest(String input, Context context) {
        UserAliasService userAliasService = new UserAliasServiceImpl();
        try {
            UserAliasResponse response = userAliasService.aliasToUser(input);
            return response;
        }
        catch (IOException x){
            return null;
        }
    }
}
