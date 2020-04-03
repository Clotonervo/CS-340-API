package dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import models.Status;
import models.User;
import net.request.FeedRequest;
import net.response.FeedResponse;
import net.response.FollowerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FeedDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;

    public FeedDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("feed");
    }

    public void postStatusToFeed(String ownerAlias, Status status){
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("feed_owner", ownerAlias, "time_stamp", status.getTimeStamp())
                            .withString("message", status.getMessage())
                            .withString("user_fname", status.getUser().getFirstName())
                            .withString("user_lname", status.getUser().getLastName())
                            .withString("user_alias", status.getUser().getAlias())
                            .withString("user_url", status.getUser().getImageUrl()));

        }
        catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
        }
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":feed_owner", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withValueMap(valueMap)
                .withKeyConditionExpression("feed_owner = :feed_owner")
                .withScanIndexForward(true)
                .withMaxPageSize(request.limit);

        if(request.getLastStatus() != null) {
            querySpec.withExclusiveStartKey("feed_owner", request.getLastStatus().getUser().getAlias(),
                    "time_stamp", request.getLastStatus().getTimeStamp());              //TODO: make sure timestamp remains the same throughout all the code
        }

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;

        try {
            items = table.query(querySpec);
            iterator = items.iterator();
            iterator.hasNext();

        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }

        boolean hasMorePages = false;
        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
        ArrayList<Status> statusList = new ArrayList<Status>();

        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }
        else {
            hasMorePages = true;
        }
        for (Item testItem: itemList) {
            User user = new User(testItem.getString("user_fname"), testItem.getString("user_lname"), testItem.getString("user_alias"), testItem.getString("user_url"));
            Status status = new Status(user, testItem.getString("message"));
            status.setTimeStamp(testItem.getLong("time_stamp"));
            statusList.add(status);
        }

        return new FeedResponse(hasMorePages, statusList);
    }
}
