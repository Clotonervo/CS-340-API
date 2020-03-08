package models.services;

import models.Status;
import net.response.PostResponse;

import java.io.IOException;

public interface PostService {
    PostResponse postStatus(Status post) throws IOException;

}
