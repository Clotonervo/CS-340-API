package dao;

import models.Follow;
import models.Status;
import models.User;
import net.request.*;
import net.response.*;

import java.util.*;

public class MockDatabase {

    private static MockDatabase instance;
    private static Map<User, List<User>> userFollowing;
    private static Map<User, List<User>> userFollowers;

    private static List<Follow> follows;
    private static User tweeterBot = new User("Tweeter", "Bot", "@TweeterBot","https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    private static User testUser = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    private static Map<User, List<Status>> userStatuses;
    private static Map<User, List<Status>> userFeeds;
    private static List<User> allUsers;

    private String authToken = "TestAuthToken";


    /*
        Constructors
     */
    public static MockDatabase getInstance() {
        if(instance == null) {
            instance = new MockDatabase();
        }

        return instance;
    }

    private MockDatabase(){
        intializeFollowData();
        initializeStatuses();
        initializeFeeds();
        allUsers = new ArrayList<>();
        allUsers.add(new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
    }


    /*
             --------------------- Get Followers

  */
    public FollowerResponse getFollowers(FollowerRequest request){
        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        List<User> allFollowers = userFollowers.get(aliasToUser(request.getFollower(), true));
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followeesIndex = getFolloweesStartingIndex(aliasToUser(request.getLastFollowee(), true), allFollowers);

                for(int limitCounter = 0; followeesIndex < allFollowers.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowers.size();
            }
        }

        Collections.sort(responseFollowers, new Comparator<User>() {
            public int compare(User o1, User o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });


        return new FollowerResponse(responseFollowers, hasMorePages);
    }

    /*
                 --------------------- Get Following

      */
    public FollowingResponse getFollowing(FollowingRequest request) {

        assert request.getLimit() >= 0;
        assert request.getFollower() != null;


        List<User> allFollowees = userFollowing.get(aliasToUser(request.getFollower(), true));
        List<User> responseFollowees = new ArrayList<>(request.getLimit());


        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(aliasToUser(request.getLastFollowee(), true), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        Collections.sort(responseFollowees, new Comparator<User>() {
            public int compare(User o1, User o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });


        return new FollowingResponse(responseFollowees, hasMorePages);
    }


    /*
                 --------------------- get following starting index

      */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

       /*
                --------------------- Generates follow data

     */
    /**
     * Generates the followee data.
     */
    private void intializeFollowData() {

        userFollowing = new HashMap<>();

        follows = getFollowGenerator().generateUsersAndFollows(100,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
        User a = new User("AA", "A", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User aa = new User("A", "A", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        follows.add(0, new Follow(a, aa));
        follows.add(0, new Follow(aa, a));
        follows.add(0, new Follow(testUser,a));
        follows.add(0, new Follow(a,testUser));
        follows.add(0,new Follow(aa,testUser));


        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = userFollowing.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                userFollowing.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        userFollowers = new HashMap<>();

        // Now do the same for a map of followers, keyed by followee to handle follower requests
        for(Follow follow : follows) {
            List<User> followers = userFollowers.get(follow.getFollowee());

            if(followers == null) {
                followers = new ArrayList<>();
                userFollowers.put(follow.getFollowee(), followers);
            }

            followers.add(follow.getFollower());
        }

        List<User> thing = new ArrayList<>();
        thing.add(testUser);

        userFollowing.put(tweeterBot, thing);
        userFollowers.put(tweeterBot, thing);

        System.out.println(userFollowers.get(a));

        return;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest){                   //When backend is up, authenticate password with username there

        for(int i = 0; i < allUsers.size(); i++){
            if(loginRequest.getUsername().equals(allUsers.get(i).getAlias())
                    && (loginRequest.getPassword().equals("password")
                    || loginRequest.getPassword().equals("x"))){
                return new LoginResponse(allUsers.get(i));
            }
        }
        return new LoginResponse("Invalid credentials");

    }

    /*
             --------------------- Initialize Statuses

  */
    private void initializeStatuses() {

        userStatuses = new HashMap<>();

        for (Map.Entry<User, List<User>> entry : userFollowing.entrySet()) {
            User currentUser = entry.getKey();
            List<Status> statusList = new ArrayList<>();
            for(int i = 1; i < 5; i++){
                statusList.add(new Status(currentUser, "Test status " + i));
            }
            statusList.add(new Status(currentUser, "www.google.com "));
            statusList.add(new Status(currentUser, "@AA"));

            userStatuses.put(currentUser, statusList);
        }

        userStatuses.put(tweeterBot, new ArrayList<Status>());

    }

    /*
         --------------------- Initialize User Feeds

*/
    private void initializeFeeds(){

        userFeeds = new HashMap<>();

        for (Map.Entry<User, List<User>> entry : userFollowing.entrySet()) {
            User currentUser = entry.getKey();
            List<Status> statusList = new ArrayList<>();
            for (User following: entry.getValue()) {
                statusList.addAll(userStatuses.get(following));
            }
            userFeeds.put(currentUser, statusList);
        }
    }

    /*
                --------------------- Sign Up User

     */
    public SignUpResponse registerNewUser(SignUpRequest signUpRequest){

        if(signUpRequest.getFirstName() == null || signUpRequest.getLastName() == null
                || signUpRequest.getPassword() == null || signUpRequest.getUsername() == null){
            return new SignUpResponse("Not all forms filled out!");
        }


        User signedUpUser = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        for (User user: allUsers) {
            if(user.getAlias().equals(signedUpUser.getAlias())){
                return new SignUpResponse("Username already exists!");
            }
        }

        List<User> newUserFollowees = new ArrayList<>();
        List<User> newUserFollowers = new ArrayList<>();


        newUserFollowees.add(tweeterBot);           //Two different arrays because if one gets altered the other would too
        newUserFollowers.add(tweeterBot);


        userFollowing.put(signedUpUser,newUserFollowees);           //Have new user follow tweeterbot
        userFollowing.get(tweeterBot).add(signedUpUser);

        userFollowers.get(tweeterBot).add(signedUpUser);            //Have tweeterBot follow new person
        userFollowers.put(signedUpUser, newUserFollowers);

        Status status = new Status(tweeterBot,"Welcome to Tweeter!");
        List<Status> currentFeed = new ArrayList<>();
        currentFeed.add(status);

        userStatuses.get(tweeterBot).add(status);
        userFeeds.put(signedUpUser, currentFeed);

        List<Status> newStatusList = new ArrayList<>();
        newStatusList.add(new Status(signedUpUser, "My first Status! Hi everybody!"));
        userStatuses.put(signedUpUser, newStatusList);

        allUsers.add(signedUpUser);


        return new SignUpResponse(signedUpUser);
    }

    /*
             --------------------- Get Story

  */
    public StoryResponse getStory(StoryRequest storyRequest){
        User user = storyRequest.getUser();

        assert storyRequest.getLimit() >= 0;
        assert storyRequest.getUser() != null;


        boolean hasMorePages = false;

        List<Status> statusList = userStatuses.get(user);
        List<Status> responseStatuses = new ArrayList<>();

        if(storyRequest.getLimit() > 0) {
            if (statusList != null) {
                int storyIndex = getStoryStartingIndex(storyRequest.getLastStatus(), statusList);

                for(int limitCounter = 0; storyIndex < statusList.size() && limitCounter < storyRequest.getLimit(); storyIndex++, limitCounter++) {
                    responseStatuses.add(statusList.get(storyIndex));
                }

                hasMorePages = storyIndex < statusList.size();
            }
        }

        System.out.println(responseStatuses);

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStoryStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }

    /*
             --------------------- Get Feed

  */
    public FeedResponse getFeed(FeedRequest feedRequest){
        User user = feedRequest.getUser();

        assert feedRequest.getLimit() >= 0;
        assert feedRequest.getUser() != null;

        boolean hasMorePages = false;

        List<Status> statusList = userFeeds.get(user);
        List<User> following = userFollowing.get(user);

        List<Status> feedResponse = new ArrayList<>();

        if(feedRequest.getLimit() > 0) {
            if (statusList != null) {
                int storyIndex = getFeedStartingIndex(feedRequest.getLastStatus(), statusList);

                for(int limitCounter = 0; storyIndex < statusList.size() && limitCounter < feedRequest.getLimit(); storyIndex++, limitCounter++) {
                    feedResponse.add(statusList.get(storyIndex));
                }

                hasMorePages = storyIndex < statusList.size();
            }
        }


        Collections.sort(feedResponse, new Comparator<Status>() {
            public int compare(Status o1, Status o2) {
                return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
            }
        });

        if(following == null){
            following = new ArrayList<>();
        }

        return new FeedResponse(hasMorePages, feedResponse, following);

    }


    private int getFeedStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }


    /*
             --------------------- Post Status

  */
    public PostResponse post(Status postedStatus){
        User user = postedStatus.getUser();

        userStatuses.get(user).add(postedStatus);

        Collections.sort(userStatuses.get(user), new Comparator<Status>() {
            public int compare(Status o1, Status o2) {
                return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
            }
        });


        //Add that status to every follower's feed
        List<User> followers = userFollowers.get(user);

        for(int i = 0; i < followers.size(); i++){
            userFeeds.get(followers.get(i)).add(postedStatus);

        }

        return new PostResponse(true, "Successfully posted!");
    }


    /*
                 --------------------- Get user from Alias

      */
    public UserAliasResponse aliasToUser(String alias){
        for (Map.Entry<User, List<User>> entry : userFollowing.entrySet()) {
            if(entry.getKey().getAlias().equals(alias)){
                return new UserAliasResponse(entry.getKey());
            }
        }
        return new UserAliasResponse();
    }

    private User aliasToUser(String alias, boolean test){
        for (Map.Entry<User, List<User>> entry : userFollowing.entrySet()) {
            if(entry.getKey().getAlias().equals(alias)){
                return entry.getKey();
            }
        }
        return null;
    }
    /*
             --------------------- isFollowing

  */
    public IsFollowingResponse isFollowing(Follow follow){
        boolean following = userFollowing.get(follow.getFollower()).contains(follow.getFollowee());
        return new IsFollowingResponse(following);
    }

    /*
             --------------------- followUser

  */
    public FollowResponse followUser(Follow follow){
        if (userFollowing.get(follow.getFollower()).add(follow.getFollowee())
                && userFollowers.get(follow.getFollowee()).add(follow.getFollower())){
            return new FollowResponse();
        }
        else{
            return new FollowResponse("Something went wrong following user");
        }
    }


    /*
             --------------------- unfollowUser

  */
    public UnfollowResponse unfollowUser(Follow follow){

        if (userFollowing.get(follow.getFollower()).remove(follow.getFollowee())
                && userFollowers.get(follow.getFollowee()).remove(follow.getFollower())){

            List<Status> statusList = new ArrayList<>(userFeeds.get(follow.getFollower()));

            Iterator<Status> itr = statusList.iterator();

            while (itr.hasNext()) {
                Status status = itr.next();

                if (status.getUser() == follow.getFollowee()) {
                    itr.remove();
                }
            }


            userFeeds.remove(follow.getFollower());
            userFeeds.put(follow.getFollower(), statusList);

            return new UnfollowResponse();
        }
        else{
            return new UnfollowResponse("Something went wrong unfollowing user");
        }
    }

    /*
             --------------------- sign out User
     */
    public SignOutResponse signOutUser(){
        //Communicate with back end to destroy auth tokens
        return new SignOutResponse(true, "Signout complete!");
    }

}
