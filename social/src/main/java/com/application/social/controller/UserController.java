package com.application.social.controller;

import com.application.social.models.User;
import com.application.social.repositary.UserRepositary;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepositary userRepositary;
    @Autowired
    private UserServiceimplementation userServiceimplementation;



    @GetMapping("/api/users")
    public List<User> getUsers()
    {
        List<User> users = userRepositary.findAll();
        return users;
    }

    @GetMapping("/api/users/{userId}")
    public Optional<User> getUserById(@PathVariable("userId") Integer id) throws Exception {
       Optional<User> user = userServiceimplementation.findUserById(id);
       return user;
    }


    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {
        User reqUser = userServiceimplementation.findUserByJwt(jwt);
        User updatedUser = userServiceimplementation.updateUser(user,reqUser.getId());
        return updatedUser;
    }

    @PutMapping("/api/users/follow/{id2}")
    public User followUserHandler( @RequestHeader("Authorization") String jwt, @PathVariable Integer id2) throws Exception {
            User reqUser = userServiceimplementation.findUserByJwt(jwt);
            User user = userServiceimplementation.followUser(reqUser.getId(), id2);
            return user;

    } 

    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) throws Exception {
        Optional<User> user = userRepositary.findById(userId);
        if(user.isEmpty())
        {
            throw new Exception("user not found with this id "+userId);
        }
        userRepositary.delete(user.get());
        return "user deleted ";
    }

    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        List<User> users = userServiceimplementation.searchUser(query);
        return users;
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {

        User user = userServiceimplementation.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }

}
