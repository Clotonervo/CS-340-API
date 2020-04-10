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
import java.util.List;

public class FeedDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;

    public FeedDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("feed");
    }

    public void postStatusToFeed(List<Item> batch) throws IOException {
        try {
            TableWriteItems tableWriteItems = new TableWriteItems("feed");
            tableWriteItems.withItemsToPut(batch);
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
            while(outcome.getUnprocessedItems().size() > 0) {
                outcome = dynamoDB.batchWriteItemUnprocessed(outcome.getUnprocessedItems());
            }
        }
        catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
            throw new IOException("Error when posting status to user feed");
        }
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":feed_owner", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withValueMap(valueMap)
                .withKeyConditionExpression("feed_owner = :feed_owner")
                .withScanIndexForward(false)
                .withMaxPageSize(request.limit);

        if(request.getLastStatus() != null) {
            querySpec.withExclusiveStartKey("feed_owner", request.getUser().getAlias(),
                    "time_stamp", request.getLastStatus().getTimeStamp());
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
            throw new IOException("Something went wrong when quereing feed: " + e.getMessage());
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
