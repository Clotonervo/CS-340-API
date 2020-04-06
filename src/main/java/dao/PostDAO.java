package dao;

import models.Follow;
import models.Status;
import models.User;
import net.request.FollowerRequest;
import net.request.StoryRequest;
import net.response.FollowerResponse;
import net.response.PostResponse;

import java.io.IOException;

public class PostDAO {

    FeedDAO feedDAO;
    StoryDAO storyDAO;
    FollowDAO followDAO;

    public PostDAO(){
        feedDAO = new FeedDAO();
        storyDAO = new StoryDAO();
        followDAO = new FollowDAO();
    }

    public PostResponse postMessage(Status status) throws IOException {
        User currentUser = status.getUser();
        FollowerRequest followerRequest = new FollowerRequest();
        followerRequest.limit = -1;
        followerRequest.followee = currentUser.getAlias();
        FollowerResponse response = followDAO.getFollowers(new FollowerRequest(status.getUser().getAlias(),
                10000000, ""));

        storyDAO.postToStory(status);

        for(User follower: response.getFollowers()){
            feedDAO.postStatusToFeed(follower.getAlias(),status);
        }

        return new PostResponse(true);
    }
}
