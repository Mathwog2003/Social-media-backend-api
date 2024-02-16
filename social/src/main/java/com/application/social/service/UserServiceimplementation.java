package com.application.social.service;

import com.application.social.config.JwtProvider;
import com.application.social.models.User;
import com.application.social.repositary.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimplementation implements UserServiceInterface{

    @Autowired
    private UserRepositary userRepositary;
    @Override
    public User registerUser(User user) {
//        User newUser = new User();
//        newUser.setId(user.getId());
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
//        User savedUser = userRepositary.save(newUser);
//        return savedUser;
        return userRepositary.save(user);
    }

    @Override
    public Optional<User> findUserById(Integer userId) throws Exception {
        Optional<User> user = userRepositary.findById(userId);
        if(user.isPresent())
        {
            return Optional.of(user.get());
        }
        throw new Exception("user not found with this id"+userId);
    }

    @Override
    public User findUserByEmail(String emailId) {
        User user = userRepositary.findByEmail(emailId);
        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws Exception {
        Optional<User> reqUser1 = findUserById(reqUserId);
        Optional<User> optionalUser2 = findUserById(userId2);

            User reqUser = reqUser1.get();
            User user2 = optionalUser2.get();

            user2.getFollowers().add(reqUser.getId());
            reqUser.getFollowings().add(user2.getId());

            // Manually handle the transaction
            userRepositary.save(reqUser);
            userRepositary.save(user2);

            return reqUser;

    }



    @Override
    public User updateUser(User user, Integer userId) throws Exception {
        Optional<User> user1 = userRepositary.findById(userId);
        if(user1.isEmpty()) {
            throw new Exception("user not found with this Id " + userId);
        }

        User oldUser = user1.get();
        if(user.getFirstName()!=null)
        {
            oldUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null)
        {
            oldUser.setLastName(user.getLastName());
        }
        if(user.getEmail()!=null)
        {
            oldUser.setEmail(user.getEmail());
        }
        if(user.getGender()!=null)
        {
            oldUser.setGender(user.getGender());
        }

        User updatedUser = userRepositary.save(oldUser);
        return updatedUser;
    }

    @Override
    public String deleteuser(Integer id) {
        Optional<User> user = userRepositary.findById(id);
        if (user.isPresent()) {
            userRepositary.delete(user.get());
            return "User deleted successfully";
        } else {
            return "User not found with ID: " + id;
        }
    }

    @Override
    public List<User> searchUser(String query) {

            return userRepositary.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepositary.findByEmail(email);
        return user;
    }
}
