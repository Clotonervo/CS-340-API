package dao;

import net.request.StoryRequest;
import net.response.StoryResponse;

public class StoryDAO {

    private MockDatabase mockDatabase;

    public StoryDAO(){
        mockDatabase = MockDatabase.getInstance();
    }

    public StoryResponse getStory(StoryRequest request){
        return mockDatabase.getStory(request);
    }
}
