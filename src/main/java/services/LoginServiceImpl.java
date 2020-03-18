package services;

import dao.AuthDAO;
import dao.UserDAO;
import models.services.LoginService;
import net.request.LoginRequest;
import net.response.LoginResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(request.getPassword().getBytes(StandardCharsets.UTF_8));
            request.password = new String(passwordHash);
        }
        catch (NoSuchAlgorithmException x){
            x.printStackTrace();
        }

        return userDAO.authenticateUser(request);
    }
}
