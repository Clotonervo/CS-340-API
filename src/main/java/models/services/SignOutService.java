package models.services;

import net.response.SignOutResponse;

import java.io.IOException;

public interface SignOutService {
    SignOutResponse signOut() throws IOException;
}
