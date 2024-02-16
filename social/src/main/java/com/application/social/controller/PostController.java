package com.application.social.controller;

import com.application.social.models.Post;
import com.application.social.models.User;
import com.application.social.service.PostServiceImplementation;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostServiceImplementation postServiceImplementation;
    @Autowired
    private UserServiceimplementation userServiceimplementation;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt,@RequestBody Post post) {
        try {
            User reqUser  = userServiceimplementation.findUserByJwt(jwt);
            Post createdPost = postServiceImplementation.createNewPost(post, reqUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId,
                                             @RequestHeader("Authorization") String jwt) {
        try {
            User reqUser  = userServiceimplementation.findUserByJwt(jwt);
            postServiceImplementation.deletePost(postId, reqUser.getId());
            return ResponseEntity.ok("Successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostsByPostId(@PathVariable Integer postId) {
        try {
            Post foundPost = postServiceImplementation.findPostByPostId(postId);
            return ResponseEntity.ok(foundPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable Integer userId) {
        List<Post> foundPost = postServiceImplementation.findPostsByUserId(userId);
        return ResponseEntity.ok(foundPost);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> allPosts = postServiceImplementation.findAllPost();
        return ResponseEntity.ok(allPosts);
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
        try {
            User reqUser  = userServiceimplementation.findUserByJwt(jwt);
            Post savedPost = postServiceImplementation.savedPost(postId, reqUser.getId());
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt) {
        try {
            User reqUser  = userServiceimplementation.findUserByJwt(jwt);
            Post likedPost = postServiceImplementation.likePost(postId, reqUser.getId());
            return ResponseEntity.ok(likedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
