package com.application.social.controller;

import com.application.social.models.Reels;
import com.application.social.models.User;
import com.application.social.service.ReelsService;
import com.application.social.service.ReelsServiceInterface;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {
    @Autowired
    private ReelsService reelsService;
    @Autowired
    private UserServiceimplementation userServiceimplementation;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reel,@RequestHeader("Authorization") String jwt)
    {
        User userReq = userServiceimplementation.findUserByJwt(jwt);
        Reels createdReels = reelsService.createReels(reel,userReq);
        return createdReels;
    }
    @GetMapping("/api/reels")
    public List<Reels> findAllReels()
    {

        List<Reels> reels = reelsService.findAllReels();
        return reels;
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findusersReels(@PathVariable Integer userId) throws Exception {

        List<Reels> reels = reelsService.findUserReels(userId);
        return reels;
    }
}
