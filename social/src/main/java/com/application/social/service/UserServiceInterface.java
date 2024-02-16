package com.application.social.service;

import com.application.social.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    public User registerUser(User user);
    public Optional<User> findUserById(Integer id) throws Exception;
    public User findUserByEmail(String emailId);
    public User followUser(Integer userId1,Integer userId2) throws Exception;
    public User updateUser(User user, Integer userId) throws Exception;
    public String deleteuser(Integer id);
    public List<User> searchUser(String query);
    public User findUserByJwt(String jwt);
}
