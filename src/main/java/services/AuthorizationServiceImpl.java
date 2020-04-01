package services;

import dao.AuthDAO;
import models.services.AuthorizationService;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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
        try {
            byte[] b = new byte[20];
            new Random().nextBytes(b);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(b);
            String authToken = new String(passwordHash);
            authDAO.insertAuthToken(alias, authToken);
            return authToken;
        }
        catch(NoSuchAlgorithmException x){
            return null;
        }
    }

}
