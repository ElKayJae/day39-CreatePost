package day39.server.repositories;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Repository
public class ImageRepository {

    @Autowired
    private AmazonS3 s3Client;
    
    public String upload(MultipartFile file, String postId) throws IOException{

        //user data
        Map<String, String> postData = new HashMap<>();
        postData.put("postid", postId);
        postData.put("uploadTime", new Date().toString());
        postData.put("originalFilename", file.getOriginalFilename());


        //metadata of the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(postData);

        
        //create a put request
        PutObjectRequest putReq = new PutObjectRequest("vttpnus", "post-image/%s".formatted(postId), file.getInputStream() ,metadata);
        
        //allow public access
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);
        
        s3Client.putObject(putReq);

        String imageUrl = "https://vttpnus.sgp1.digitaloceanspaces.com/post-image/%s".formatted(postId);
        
        return imageUrl;

    }
}
