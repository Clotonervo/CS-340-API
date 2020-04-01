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
        valueMap.put(":user_alias", request.getUser().getAlias());

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("user_alias = :user_alias")
                .withValueMap(valueMap)
                .withScanIndexForward(true)
                .withMaxPageSize(request.limit);

        if(request.getLastStatus() != null) {
            querySpec.withExclusiveStartKey("user_alias", request.getLastStatus().getUser().getAlias(),
                    "time_stamp", request.getLastStatus().getTimeStamp());              //TODO: make sure timestamp remains the same throughout all the code
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
        ArrayList<Item> itemList = new ArrayList<Item>(outcome.getItems());
        ArrayList<Status> statusList = new ArrayList<Status>();

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
}
