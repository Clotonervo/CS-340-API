package dao;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import net.response.SignOutResponse;


public class AuthDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;

    public AuthDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("authentication");
    }

    public SignOutResponse signOut(String alias) throws IOException {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("user_alias", alias);

        try {
            table.deleteItem(deleteItemSpec);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException("Error deleting authtoken");
        }
        return new SignOutResponse(true);
    }

    public boolean validateToken(String token) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":auth_token", token);

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("auth_token = :auth_token")
                .withValueMap(valueMap)
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            items = table.getIndex("auth_token-index").query(querySpec);
            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
            }
        } catch (Exception e) {
            System.err.println("Unable to query");
            System.err.println(e.getMessage());
            throw new IOException("Error validating authToken");
        }

        QueryOutcome outcome = items.getLastLowLevelResult();
        if (outcome.getItems().size() == 0){
            return false;
        }
        else if (outcome.getItems().get(0).getLong("expiration") - System.currentTimeMillis() < 0){
            return false;
        }
        else {
            return true;
        }
    }

    public void insertAuthToken(String alias, String token) throws IOException {
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("user_alias", alias)
                            .withString("auth_token", token)
                            .withLong("expiration", System.currentTimeMillis()+20*60*1000));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException("Error adding authToken: " + e.getMessage());
        }
    }
}
