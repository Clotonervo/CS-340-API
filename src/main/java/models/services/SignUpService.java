package models.services;

import net.request.SignUpRequest;
import net.response.SignUpResponse;

import java.io.IOException;

public interface SignUpService {
    SignUpResponse registerUser(SignUpRequest request) throws IOException;

}
