package com.application.social.service;

import com.application.social.models.Chat;
import com.application.social.models.Message;
import com.application.social.models.User;

import java.util.List;

public interface MessageServiceInterface {

 public Message createMessage(User user, Integer chatId, Message req ) throws Exception;
 public List<Message> findChatMessages(Integer chatId) throws Exception;

}
