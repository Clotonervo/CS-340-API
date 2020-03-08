package models.services;

import net.request.LoginRequest;
import net.response.LoginResponse;

import java.io.IOException;

public interface LoginService {
    LoginResponse login(LoginRequest request) throws IOException;

}
