package services;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dao.UserDAO;
import models.services.SignUpService;
import net.request.SignUpRequest;
import net.response.SignUpResponse;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SignUpServiceImpl implements SignUpService {
    private final String bucketName = "tweeter-user-images";

    @Override
    public SignUpResponse registerUser(SignUpRequest request) throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(request.getPassword().getBytes(StandardCharsets.UTF_8));
            request.setPassword(new String(passwordHash));
            String imageURL = convertToImage(request.getImage());
            request.setImage(imageURL);
            System.out.println(imageURL);
        }
        catch (NoSuchAlgorithmException x){
            x.printStackTrace();
        }
        return userDAO.registerUser(request);
    }


    private String convertToImage(String uploadedImage){
        byte[] byteArrayImage = Base64.decodeBase64(uploadedImage);

        InputStream fis = new ByteArrayInputStream(byteArrayImage);

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-2")
                .build();

        Random rand = new Random();
        String key = "image" + Math.abs(rand.nextInt());

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(byteArrayImage.length);
        meta.setContentType("image/png");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, fis, meta).withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(putObjectRequest);
        AmazonS3Client client = (AmazonS3Client) s3;

        return client.getResourceUrl(bucketName, key);
    }
}
