package com.application.social.service;

import com.application.social.models.Chat;
import com.application.social.models.User;

import java.util.List;

public interface ChatServiceInterface {


    public Chat createChat(User reqUser, User user2);

    public Chat findChatById(Integer chatId) throws Exception;


    public List<Chat> findUsersChat(Integer userId);
}
