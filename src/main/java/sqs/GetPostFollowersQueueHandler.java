package sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import dao.FollowDAO;
import json.Serializer;
import models.FeedUpdate;
import models.Status;
import models.User;
import net.request.FollowerRequest;
import net.response.FollowerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GetPostFollowersQueueHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();


        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Status post = Serializer.deserialize(msg.getBody(), Status.class);
            FollowDAO followDAO = new FollowDAO();
            String queueUrl = "https://sqs.us-east-2.amazonaws.com/188074185154/PostToFeedsQueue";
            List<User> userList = new ArrayList<>();


            try {
                User currentUser = post.getUser();
                FollowerRequest followerRequest = new FollowerRequest();
                followerRequest.limit = -1;
                followerRequest.followee = currentUser.getAlias();
                followerRequest.lastFollower = "";
                boolean loopMore = false;

                do {
                    FollowerResponse response = followDAO.getFollowers(followerRequest);

                    loopMore = response.hasMorePages;
                    followerRequest.lastFollower = response.getLastFollower();

                    System.out.println("---------------------------- Number of followers:");
                    System.out.println(response.getFollowers().size());
                    System.out.println("last follower:");
                    System.out.println(response.getLastFollower());
                    System.out.println(response.hasMorePages);

                    for (int i = 0; i < response.getFollowers().size(); i++) {
                        userList.add(response.getFollowers().get(i));
                    }

                    System.out.println("total users in list:");
                    System.out.println(userList.size());
                }while(loopMore);

                List<User> userListToSend = new ArrayList<>();
                List<SendMessageRequest> messages = new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    userListToSend.add(userList.get(i));
                    if (userListToSend.size() == 25 || i == userList.size() - 1) {
                        FeedUpdate feedUpdate = new FeedUpdate(userListToSend, post);
                        String messageBody = Serializer.serialize(feedUpdate);


                        SendMessageRequest send_msg_request = new SendMessageRequest()
                                .withQueueUrl(queueUrl)
                                .withMessageBody(messageBody);

                        messages.add(send_msg_request);

//                        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
                        userListToSend.clear();
                    }
                }
                System.out.println("number of messages : ");
                System.out.println(messages.size());

                int i = 1;
                for (SendMessageRequest request : messages) {
                    SendMessageResult send_msg_result = sqs.sendMessage(request);
                    System.out.print(i + ", ");
                    i++;
                }

                System.out.println("All messages sent!");
            }
            catch(IOException x){
                System.out.println(x.getMessage());
                System.out.println("__________________ ERROR _______________");
                throw new RuntimeException("Error while getting user followers to post!");
            }
        }

        return null;
    }
}





