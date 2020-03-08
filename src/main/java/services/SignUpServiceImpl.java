package services;

import dao.UserDAO;
import models.services.SignUpService;
import net.request.SignUpRequest;
import net.response.SignUpResponse;

import java.io.IOException;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse registerUser(SignUpRequest request) throws IOException {
        UserDAO userDAO = new UserDAO();
        return userDAO.registerUser(request);
    }
}
