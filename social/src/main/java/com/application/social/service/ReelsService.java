package com.application.social.service;

import com.application.social.models.Reels;
import com.application.social.models.User;
import com.application.social.repositary.ReelsRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsService implements ReelsServiceInterface{

    @Autowired
    private ReelsRepositary reelsRepositary;
    @Autowired
    private UserServiceimplementation userServiceimplementation;
    @Override
    public Reels createReels(Reels reels, User user) {
        Reels createReel = reelsRepositary.save(reels);
        return createReel;
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepositary.findAll();
    }

    @Override
    public List<Reels> findUserReels(Integer userId) throws Exception {
        userServiceimplementation.findUserById(userId);
        return reelsRepositary.findByUserId(userId);
    }
}
