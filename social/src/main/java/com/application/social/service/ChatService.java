package com.application.social.service;

import com.application.social.models.Chat;
import com.application.social.models.User;
import com.application.social.repositary.ChatRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements ChatServiceInterface{

    @Autowired
    private ChatRepositary chatRepositary;
    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExist = chatRepositary.findChatByUsersId(user2,reqUser);
        if(isExist!=null)
        {
            return isExist;
        }
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimestamp(LocalDateTime.now());
        return chatRepositary.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> opt = chatRepositary.findById(chatId);
        if(opt.isEmpty())
        {
            throw new Exception("Chat not found with this id - "+chatId);
        }
        return opt.get();
    }


    @Override
    public List<Chat> findUsersChat(Integer userId) {

        return chatRepositary.findByUsersId(userId);
    }
}
