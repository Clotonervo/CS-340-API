package services;

import dao.AuthDAO;
import dao.UserDAO;
import models.services.LoginService;
import net.request.LoginRequest;
import net.response.LoginResponse;

import java.io.IOException;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        return userDAO.authenticateUser(request);
    }
}
