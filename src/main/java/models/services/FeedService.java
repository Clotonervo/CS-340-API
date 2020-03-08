package models.services;

import net.request.FeedRequest;
import net.response.FeedResponse;

import java.io.IOException;

public interface FeedService {
    FeedResponse getFeed(FeedRequest request) throws IOException;
}
