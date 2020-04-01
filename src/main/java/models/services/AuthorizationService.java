package models.services;

import java.io.IOException;

public interface AuthorizationService {
    boolean isValid(String authToken) throws IOException;
    String generateAuthToken(String alias) throws IOException;
}
