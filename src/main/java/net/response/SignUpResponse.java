package net.response;

import models.User;

public class SignUpResponse extends Response{

    private User signedInUser;

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
}
