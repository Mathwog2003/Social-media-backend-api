package com.application.social.service;

import com.application.social.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostServcieInterface {
    Post createNewPost(Post post, Integer userId) throws Exception;
    String deletePost(Integer postId,Integer userId) throws Exception;
    List<Post> findPostsByUserId(Integer userId);
    Post findPostByPostId(Integer postId) throws Exception;
    List<Post> findAllPost();
    Post savedPost(Integer postId, Integer userId) throws Exception;
    Post likePost(Integer postId, Integer userId) throws Exception;
}
