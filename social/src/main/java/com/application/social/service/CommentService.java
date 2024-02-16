package com.application.social.service;

import com.application.social.models.Comment;
import com.application.social.models.Post;
import com.application.social.models.User;
import com.application.social.repositary.CommentRepositary;
import com.application.social.repositary.PostRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService implements CommentServiceInterface {
    @Autowired
    private PostServiceImplementation postServiceImplementation;
    @Autowired
    private  UserServiceimplementation userServiceimplementation;
    @Autowired
    private CommentRepositary commentRepositary;
    @Autowired
    private PostRepositary postRepositary;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        Optional<User> user = userServiceimplementation.findUserById(userId);
        Post post  = postServiceImplementation.findPostByPostId(postId);
        comment.setUser(user.get());
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepositary.save(comment);
        post.getComments().add(comment);
        postRepositary.save(post);


        return savedComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> comment = commentRepositary.findById(commentId);
        if(comment.isEmpty())
        {
            throw new Exception("comment not exist");
        }
        return comment.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
       Comment comment= findCommentById(commentId);
        Optional<User> user = userServiceimplementation.findUserById(userId);
        if(!comment.getLiked().contains(user))
        {
            comment.getLiked().add(user.get());
        }
        else
        {
            comment.getLiked().remove(user);
        }
        return commentRepositary.save(comment);
    }
}
