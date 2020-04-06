package services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import dao.StoryDAO;
import json.Serializer;
import models.Status;
import models.services.PostService;
import net.response.PostResponse;

import java.io.IOException;

public class PostServiceImpl implements PostService {
    @Override
    public PostResponse postStatus(Status post) throws IOException {
        StoryDAO storyDao = new StoryDAO();

        String queueUrl = "https://sqs.us-east-2.amazonaws.com/188074185154/GetPostFollowersQueue";
        String messageBody = Serializer.serialize(post);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        storyDao.postToStory(post);

        return new PostResponse(true);
    }
}
