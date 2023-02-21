package day39.server.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Post {
    
    private String postId;
    private String text;
    private String title;
    private String imageUrl;
    private Integer like;
    private Integer dislike;

    public Integer getLike() {
        return like;
    }
    public void setLike(Integer like) {
        this.like = like;
    }
    public Integer getDislike() {
        return dislike;
    }
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder().add("postid", getPostId())
                                        .add("title", getTitle())
                                        .add("imageurl", getImageUrl())
                                        .add("text", getText())
                                        .add("like", getLike())
                                        .add("dislike", getDislike())
                                        .build();
                                        
    }

    public Document toDocument(){
        Document doc = new Document();
        doc.put("postId", getPostId());
        doc.put("title", getTitle());
        doc.put("imageurl", getImageUrl());
        doc.put("text", getText());
        return doc;
    }

    public static Post createFromDocument(Document doc){
        Post post = new Post();
        post.setPostId(doc.getString("postId"));
        post.setImageUrl(doc.getString("imageurl"));
        post.setText(doc.getString("text"));
        post.setTitle(doc.getString("title"));
        return post;
    }
}
