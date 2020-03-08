package net.response;

public class FollowResponse extends Response {

    public FollowResponse()
    {
        super(true);
    }

    public FollowResponse(String message)
    {
        super(false, message);
    }

    @Override
    public boolean isSuccess()
    {
        return super.isSuccess();
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
