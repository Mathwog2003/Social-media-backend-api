package com.application.social.service;

import com.application.social.models.Post;
import com.application.social.models.User;
import com.application.social.repositary.PostRepositary;
import com.application.social.repositary.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostServcieInterface{

    @Autowired
    private PostRepositary postRepositary;
    @Autowired
    private UserServiceimplementation userServiceimplementation;
    @Autowired
    private UserRepositary userRepositary;
    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        Optional<User> user = userServiceimplementation.findUserById(userId);
        Post newpost = new Post();
        newpost.setCaption(post.getCaption());
        newpost.setImage(post.getImage());
        newpost.setCreatedAt(post.getCreatedAt());
        newpost.setCreatedAt(LocalDateTime.now());
        newpost.setVideo(post.getVideo());
        newpost.setUser(user.get());
        return postRepositary.save(newpost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Optional<User> user = userServiceimplementation.findUserById(userId);
        Post post = findPostByPostId(postId);

        if (post.getUser().getId()  != user.get().getId()) {
            throw new Exception("You can't delete another user's post");
        }
        postRepositary.delete(post);

        return "Post has been deleted";
    }

    @Override
    public List<Post> findPostsByUserId(Integer userId) {
        List<Post> posts = postRepositary.findPostByUserId(Long.valueOf(userId));
        return posts;
    }

    @Override
    public Post findPostByPostId(Integer postId) throws Exception {
        Optional<Post> post = postRepositary.findById(postId);
        if(post.isEmpty())
        {
            throw  new Exception("Post not found with this id"+postId);
        }
        return post.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepositary.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {

        Post post = findPostByPostId(postId);
        Optional<User> user = userServiceimplementation.findUserById(userId);

        if (user.isPresent()) {
            if (user.get().getSavedPost().contains(post)) {
                // The post is already saved by the user
                user.get().getSavedPost().remove(post);
            } else {
                // Save the post for the user
                user.get().getSavedPost().add(post);
                userRepositary.save(user.get());
            }
            return post;
        }else {
            throw new Exception("User not found");
        }
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostByPostId(postId);

        if (post != null) {
            Optional<User> user = userServiceimplementation.findUserById(userId);

            if (user.isPresent()) {
                if (post.getLiked().contains(user.get())) {
                    post.getLiked().remove(user.get());
                } else {
                    post.getLiked().add(user.get());
                }

                return postRepositary.save(post);
            } else {
                throw new Exception("User not found");
            }
        } else {
            throw new Exception("Post not found");
        }
    }
    }

