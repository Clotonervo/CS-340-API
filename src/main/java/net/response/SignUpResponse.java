package net.response;

import models.User;

public class SignUpResponse extends Response{

    private User signedInUser;
    private String authToken;

    public SignUpResponse(String message) {
        super(false,message);
    }

    public SignUpResponse(User signedInUser){
        super(true, null);
        this.signedInUser = signedInUser;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public boolean isSuccess() {
        return super.isSuccess();
    }

    public String getAuthToken(){
        return this.authToken;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
}
