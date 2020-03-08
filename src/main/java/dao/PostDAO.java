package dao;

import models.Status;
import net.response.PostResponse;

public class PostDAO {
    private MockDatabase mockDatabase;

    public PostDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public PostResponse postMessage(Status message){
        return mockDatabase.post(message);
    }
}
