package day39.server.services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import day39.server.models.Post;
import day39.server.repositories.ImageRepository;
import day39.server.repositories.PostRepository;
import day39.server.repositories.VoteRepository;
import jakarta.json.JsonObject;

@Service
public class PostService {
    
    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private VoteRepository voteRepo;

    public Optional<JsonObject> upload(Post post, MultipartFile image){

        String postId = UUID.randomUUID().toString().substring(0,8);
        post.setPostId(postId);
        try {
            String url = imageRepo.upload(image, postId);
            post.setImageUrl(url);
            post.setLike(0);
            post.setDislike(0);
            postRepo.insertPost(post);
            voteRepo.intialise(postId);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(post.toJson());
    }

    public Optional<Post> getPost(String id){

        Optional<Post> opt = postRepo.getPost(id);
        if (opt.isEmpty()) return Optional.empty();

        Post post = opt.get();
        Map<String,Integer> voteMap = voteRepo.getVotes(id);
        post.setLike(voteMap.get("like"));
        post.setDislike(voteMap.get("dislike"));

        return Optional.of(post);
    }

    public void like(String id){
        voteRepo.like(id);
    }

    public void dislike(String id){
        voteRepo.dislike(id);
    }
}
