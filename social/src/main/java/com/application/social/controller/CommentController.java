package com.application.social.controller;

import com.application.social.models.Comment;
import com.application.social.models.User;
import com.application.social.service.CommentService;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServiceimplementation userServiceimplementation;

//    @PostMapping("/api/comments/post/{postId}")
//    public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) throws Exception {
//        User user = userServiceimplementation.findUserByJwt(jwt);
//        Comment createdComment = commentService.createComment(comment,postId,user.getId());
//        return createdComment;
//    }
//
//    @PutMapping("/api/comments/like/{commentId}")
//    public Comment likeComment( @RequestHeader("Authorization") String jwt, @PathVariable Integer commentId) throws Exception {
//        User user = userServiceimplementation.findUserByJwt(jwt);
//        Comment likedComment = commentService.likeComment(commentId,user.getId());
//        return likedComment;
//    }

    @PostMapping("/api/comments/post/{postId}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) {
        try {
            User user = userServiceimplementation.findUserByJwt(jwt);
            Comment createdComment = commentService.createComment(comment, postId, user.getId());
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/api/comments/like/{commentId}")
    public ResponseEntity<?> likeComment(@RequestHeader("Authorization") String jwt, @PathVariable Integer commentId) {
        try {
            User user = userServiceimplementation.findUserByJwt(jwt);
            Comment likedComment = commentService.likeComment(commentId, user.getId());
            return ResponseEntity.ok(likedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
