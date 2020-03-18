package services;

import dao.UserDAO;
import models.services.SignUpService;
import net.request.SignUpRequest;
import net.response.SignUpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse registerUser(SignUpRequest request) throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(request.getPassword().getBytes(StandardCharsets.UTF_8));
            request.setPassword(new String(passwordHash));
        }
        catch (NoSuchAlgorithmException x){
            x.printStackTrace();
        }
        return userDAO.registerUser(request);
        //Here we will send the image to S3
    }
}
