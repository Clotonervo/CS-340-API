package services;

import dao.PostDAO;
import models.Status;
import models.services.PostService;
import net.response.PostResponse;

import java.io.IOException;

public class PostServiceImpl implements PostService {
    @Override
    public PostResponse postStatus(Status post) throws IOException {
        PostDAO postDAO = new PostDAO();
        return postDAO.postMessage(post);
    }
}
