package models.services;

import net.response.UserAliasResponse;

import java.io.IOException;

public interface UserAliasService {
    UserAliasResponse aliasToUser(String alias) throws IOException;
}
