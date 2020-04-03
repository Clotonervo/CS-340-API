package dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import models.User;
import net.request.LoginRequest;
import net.request.SignUpRequest;
import net.response.LoginResponse;
import net.response.SignUpResponse;
import net.response.UserAliasResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class UserDAO {

    private DynamoDB dynamoDB;
    private AmazonDynamoDB client;
    private Table table;

    public UserDAO(){
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        dynamoDB = new DynamoDB(client);
        table = dynamoDB.getTable("user");
    }

    /*
                    Login a user

     */
    public LoginResponse authenticateUser(LoginRequest request) throws IOException {                //TODO: factor all non database logic to service
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_alias", request.getUsername());

        Item result = null;

        try {
            System.out.println("Attempting to read the item...");
            result = table.getItem(spec);
        }
        catch (Exception e) {
            System.err.println("Unable to read item ");
            System.err.println(e.getMessage());
            throw new IOException("Database error");
        }

        if (result == null){
            return new LoginResponse("Invalid username");
        }
        else if (!result.getString("user_password").equals(request.getPassword())) {
            return new LoginResponse("Invalid password");
        }
        else {
            User userToReturn = new User(result.getString("user_fname"), result.getString("user_lname"),
                    request.getUsername(), result.getString("user_url"));
            return new LoginResponse(userToReturn);
        }
    }

    /*
                Sign up a new User

     */
    public SignUpResponse registerUser(SignUpRequest request) throws IOException {
        if(aliasToUser(request.getUsername()).isSuccess()){
            return new SignUpResponse("User already exists!");
        }

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("user_alias", request.getUsername())
                            .withString("user_fname", request.getFirstName())
                            .withString("user_lname", request.getLastName())
                            .withString("user_url", request.getImage())
                            .withString("user_password", request.getPassword()));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }
        User signedInUser = new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getImage());
        return new SignUpResponse(signedInUser);
    }

    /*
            Alias to User

     */
    public UserAliasResponse aliasToUser(String alias) throws IOException {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":user_alias", alias);

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("user_alias = :user_alias")
                .withValueMap(valueMap)
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            items = table.query(querySpec);
            iterator = items.iterator();
            iterator.hasNext();

        } catch (Exception e) {
            System.err.println("Unable to query");
            System.err.println(e.getMessage());
            throw new IOException("Database Error");
        }

        QueryOutcome outcome = items.getLastLowLevelResult();
        if (outcome.getItems().size() == 0){
            return new UserAliasResponse("Doesn't exist");
        }
        else{
            item = outcome.getItems().get(0);
            User userToReturn = new User(item.getString("user_fname"), item.getString("user_lname"), alias, item.getString("user_url"));
            return new UserAliasResponse(userToReturn);
        }
    }
}
