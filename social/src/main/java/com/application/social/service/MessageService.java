package com.application.social.service;

import com.application.social.models.Chat;
import com.application.social.models.Message;
import com.application.social.models.User;
import com.application.social.repositary.ChatRepositary;
import com.application.social.repositary.MessageRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageService implements MessageServiceInterface{
    @Autowired
    private MessageRepositary messageRepositary;
    @Autowired
    private ChatRepositary chatRepositary;
    @Autowired
    private ChatService chatService;
    @Override
    public Message createMessage(User user, Integer chatId, Message req) throws Exception {
        Message message = new Message();
        Chat chat = chatService.findChatById(chatId);
        message.setChat(chat);
        message.setContent(req.getContent());
        message.setImage(req.getImage());
        message.setUser(user);
        message.setTimeStamp(LocalDateTime.now());
        Message savedMessage = messageRepositary.save(message);
        chat.getMessages().add(savedMessage);
        chatRepositary.save(chat);
        return savedMessage;
    }

    @Override
    public List<Message> findChatMessages(Integer chatId) throws Exception {
        Chat chat = chatService.findChatById(chatId);

        return messageRepositary.findByChatId(chatId);
    }
}
