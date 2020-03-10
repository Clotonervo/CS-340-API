package services;

import dao.UserDAO;
import models.User;
import models.services.UserAliasService;
import net.response.UserAliasResponse;

import java.io.IOException;

public class UserAliasServiceImpl implements UserAliasService {
    @Override
    public UserAliasResponse aliasToUser(String alias) throws IOException {
        UserDAO userDAO = new UserDAO();
        return userDAO.aliasToUser(alias);
    }
}
