package dao;

import net.response.SignOutResponse;

public class AuthDAO {

    private MockDatabase mockDatabase;

    public AuthDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public SignOutResponse signOut() {
        return mockDatabase.signOutUser();
    }
}
