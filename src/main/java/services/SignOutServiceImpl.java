package services;

import dao.AuthDAO;
import models.services.SignOutService;
import net.response.SignOutResponse;

import java.io.IOException;

public class SignOutServiceImpl implements SignOutService {
    @Override
    public SignOutResponse signOut(String alias) throws IOException {
        AuthDAO authDAO = new AuthDAO();
        return authDAO.signOut(alias);
    }
}
