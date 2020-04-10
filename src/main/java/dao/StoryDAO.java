package dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import models.Status;
import net.request.StoryRequest;
import net.response.StoryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StoryDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;

    public StoryDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("stories");
    }

    public StoryResponse getStory(StoryRequest request) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":story_owner", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withValueMap(valueMap)
                .withKeyConditionExpression("story_owner = :story_owner")
                .withScanIndexForward(false)
                .withMaxPageSize(request.limit);

        if(request.getLastStatus() != null) {
            querySpec.withExclusiveStartKey("story_owner", request.getLastStatus().getUser().getAlias(),
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
            throw new IOException("Database Error");
        }

        boolean hasMorePages = false;
        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<>(outcome.getItems());
        ArrayList<Status> statusList = new ArrayList<>();

        if(outcome.getQueryResult().getLastEvaluatedKey() == null){
            hasMorePages = false;
        }
        else {
            hasMorePages = true;
        }
        for (Item testItem: itemList) {
            Status status = new Status(request.getUser(), testItem.getString("message"));
            status.setTimeStamp(testItem.getLong("time_stamp"));
            statusList.add(status);
        }

        return new StoryResponse(statusList, hasMorePages);
    }

    public void postToStory(Status status) throws IOException {
//        System.out.println(status.getUser().getAlias());
//        System.out.println(status.getMessage());
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("story_owner", status.getUser().getAlias(), "time_stamp", status.getTimeStamp())
                            .withString("message", status.getMessage()));

        }
        catch (Exception e) {
            System.err.println("Unable to add item");
            System.err.println(e.getMessage());
            System.out.print("something went wrong");
            throw new IOException("Database error");
        }
//        System.out.println("Everything went well");
    }
}
