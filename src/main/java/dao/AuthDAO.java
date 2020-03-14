package dao;


import java.io.UnsupportedEncodingException;
import net.response.SignOutResponse;


public class AuthDAO {

    private MockDatabase mockDatabase;

    public AuthDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public SignOutResponse signOut(String alias) {
        return mockDatabase.signOutUser(alias);
    }

    public String generateToken(){
        return "token";
    }

    public boolean validateToken(String token) {
        return token.equals("token");
    }
}
