package models.services;

import net.response.SignOutResponse;

import java.io.IOException;

public interface SignOutService {
    SignOutResponse signOut(String alias) throws IOException;
}
