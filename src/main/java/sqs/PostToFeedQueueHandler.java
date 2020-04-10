package sqs;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import dao.FeedDAO;
import json.Serializer;
import models.FeedUpdate;
import models.Status;
import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostToFeedQueueHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        FeedDAO feedDAO = new FeedDAO();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            FeedUpdate update = Serializer.deserialize(msg.getBody(), FeedUpdate.class);
            Status post = update.getStatus();
            System.out.println("----------- Recieved array of size:");
            System.out.println(update.getFollower().size());
            List<Item> itemsList = new ArrayList<>();
            for (int i = 0; i < update.getFollower().size(); i++) {
                User user = update.getFollower().get(i);
                try {
                    Item item = new Item().withPrimaryKey("feed_owner", user.getAlias(), "time_stamp", post.getTimeStamp())
                            .withString("message", post.getMessage())
                            .withString("user_fname", post.getUser().getFirstName())
                            .withString("user_lname", post.getUser().getLastName())
                            .withString("user_alias", post.getUser().getAlias())
                            .withString("user_url", post.getUser().getImageUrl());

                    itemsList.add(item);

                    if(itemsList.size() == 25) {
                        feedDAO.postStatusToFeed(itemsList);
                        itemsList.clear();
                    }
                    else if (i == update.getFollower().size() - 1){
                        feedDAO.postStatusToFeed(itemsList);
                        itemsList.clear();
                    }
                }
                catch(IOException x){
                    System.out.println(x.getMessage());
                    throw new RuntimeException("Error while posting statuses to feeds!");
                }
            }
        }

        return null;
    }
}
