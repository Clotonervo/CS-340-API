package services;

import dao.AuthDAO;
import models.services.AuthorizationService;

public class AuthorizationServiceImpl implements AuthorizationService {
    private AuthDAO authDAO;

    public AuthorizationServiceImpl(){
        authDAO = new AuthDAO();
    }

    @Override
    public boolean isValid(String authToken) {
        return authDAO.validateToken(authToken);
    }

    @Override
    public String generateAuthToken() {
        return authDAO.generateToken();
    }
}
