package services;

import dao.AuthDAO;
import models.services.AuthorizationService;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class AuthorizationServiceImpl implements AuthorizationService {
    private AuthDAO authDAO;

    public AuthorizationServiceImpl(){
        authDAO = new AuthDAO();
    }

    @Override
    public boolean isValid(String authToken) throws IOException {
        return authDAO.validateToken(authToken);
    }

    @Override
    public String generateAuthToken(String alias) throws IOException {
        String authToken = UUID.randomUUID().toString();
        authDAO.insertAuthToken(alias, authToken);
        return authToken;
    }

}
