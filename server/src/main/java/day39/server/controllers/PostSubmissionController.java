package day39.server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import day39.server.models.Post;
import day39.server.services.PostService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping("/api")
public class PostSubmissionController {

    @Autowired
    private PostService postService;
    
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestPart MultipartFile image, @RequestPart String title, @RequestPart String text){
        Post post = new Post();
        post.setText(text);
        post.setTitle(title);
        Optional<JsonObject> opt = postService.upload(post, image);
        if (opt.isEmpty()) {
            JsonObject error = Json.createObjectBuilder().add("error", "failed to create post").build();
            return ResponseEntity.internalServerError().body(error.toString());
        }
        return ResponseEntity.ok().body(opt.get().toString());
    }

    @GetMapping(path = "post/{postId}")
    @ResponseBody
    public ResponseEntity<String> getPost(@PathVariable String postId){
        System.out.println(postId);
        Optional<Post> opt = postService.getPost(postId);
        if (opt.isEmpty()) {
            JsonObject error = Json.createObjectBuilder().add("error", "failed to retrieve post").build();
            return ResponseEntity.internalServerError().body(error.toString());
        }
        return ResponseEntity.ok().body(opt.get().toJson().toString());

    }

    @GetMapping(path = "post/{postId}/{vote}")
    @ResponseBody
    public ResponseEntity<String> vote(@PathVariable String postId, @PathVariable String vote){
        if (vote.equals("like")) postService.like(postId);
        if (vote.equals("dislike")) postService.dislike(postId);

        return getPost(postId);
    }
}
