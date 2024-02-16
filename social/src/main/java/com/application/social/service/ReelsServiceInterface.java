package com.application.social.service;

import com.application.social.models.Reels;
import com.application.social.models.User;

import java.util.List;

public interface ReelsServiceInterface {
    public Reels createReels(Reels reels, User user);
    public List<Reels> findAllReels();
    public List<Reels> findUserReels(Integer userId) throws Exception;
}
