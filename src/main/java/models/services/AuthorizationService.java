package models.services;

public interface AuthorizationService {
    boolean isValid(String authToken);
    String generateAuthToken();
}
