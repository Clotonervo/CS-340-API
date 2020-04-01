package dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import models.Follow;
import models.User;
import net.request.FollowerRequest;
import net.request.FollowingRequest;
import net.response.*;

import java.io.IOException;
import java.util.*;

public class FollowDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;


    public FollowDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("follows");
    }

    /*
            Follow a user

     */
    public FollowResponse followUser(Follow follow) throws IOException{
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("follower_handle", follow.getFollower().getAlias())
                            .withString("followee_handle", follow.getFollowee().getAlias())
                    .withString("follower_fname", follow.getFollower().getFirstName())
                            .withString("followee_fname", follow.getFollowee().getFirstName())
                            .withString("follower_lname", follow.getFollower().getLastName())
                            .withString("followee_lname", follow.getFollowee().getLastName())
                            .withString("follower_url", follow.getFollower().getImageUrl())
                            .withString("followee_url", follow.getFollowee().getImageUrl()));

        } catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }

        return new FollowResponse();
    }


    /*
            Unfollow a user

     */
    public UnfollowResponse unfollowUser(Follow follow) throws IOException{

        try {
            table.deleteItem(new DeleteItemSpec()
                    .withPrimaryKey("follower_handle", follow.getFollower().getAlias(), "followee_handle", follow.getFollowee().getAlias()));
        }
        catch (Exception e) {
            System.err.println("Unable to delete item");
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }
        return new UnfollowResponse();
    }


    /*
            IsFollowing

     */
    public IsFollowingResponse isFollowing(Follow follow) throws IOException {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", follow.getFollower().getAlias(),
                "followee_handle", follow.getFollowee().getAlias());

        try {
            Item outcome = table.getItem(spec);
        }
        catch (Exception e) {
            System.err.println("Unable to read item ");
            throw new IOException("Database Error");
        }

        return new IsFollowingResponse(true);
    }

    /*
            Get all the user's followers

     */
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {      //TODO: factor out none database stuff to services
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":followee_handle", request.getFollower());

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("followee_handle = :followee_handle")
                .withValueMap(valueMap)
                .withScanIndexForward(true)
                .withMaxPageSize(request.limit);

        if(request.lastFollower != null) {
            querySpec.withExclusiveStartKey("follower_handle", request.getFollower(),
                    "followee_handle", request.getLastFollowee());
        }

        ItemCollection<QueryOutcome> items = null;

        try {
            items = table.getIndex("followee_handle-follower_handle-index").query(querySpec);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        boolean hasMorePages = false;
        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
        ArrayList<User> followerList = new ArrayList<User>();
        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }
        else {
            hasMorePages = true;
        }
        for (Item testItem: itemList) {
            User user = new User(testItem.getString("follower_fname"), testItem.getString("follower_lname"), testItem.getString("follower_url"));
            followerList.add(user);
        }

        return new FollowerResponse(followerList, hasMorePages);
    }

    /*
            Get all users that you are currently following

     */
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":follower_handle", request.getFollower());

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("follower_handle = :follower_handle")
                .withValueMap(valueMap)
                .withScanIndexForward(true)
                .withMaxPageSize(request.limit);

        if(request.getLastFollowee() != null) {
            querySpec.withExclusiveStartKey("follower_handle", request.getFollower(),
                    "followee_handle", request.getLastFollowee());
        }

        ItemCollection<QueryOutcome> items = null;

        try {
            items = table.query(querySpec);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }

        boolean hasMorePages = false;
        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<>(outcome.getItems());
        ArrayList<User> followingList = new ArrayList<>();

        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }
        else {
            hasMorePages = true;
        }
        for (Item testItem: itemList) {
            User user = new User(testItem.getString("followee_fname"), testItem.getString("followee_lname"), testItem.getString("followee_url"));
            followingList.add(user);
        }

        return new FollowingResponse(followingList, hasMorePages);
    }


}
