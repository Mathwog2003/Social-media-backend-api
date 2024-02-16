package com.application.social.controller;

import com.application.social.models.Message;
import com.application.social.models.User;
import com.application.social.service.MessageService;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserServiceimplementation userServiceimplementation;

    @PostMapping("/api/messages/chat/{chatId}")
    public Message createMessage(@RequestBody Message req, @RequestHeader("Authorization") String jwt,@PathVariable Integer chatId) throws Exception {
        User user = userServiceimplementation.findUserByJwt(jwt);
    Message message = messageService.createMessage(user,chatId,req);
    return message;
    }
    @GetMapping("/api/messages/chat/{chatId}")
    public List<Message> findChatsMessage( @RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) throws Exception {
        User user = userServiceimplementation.findUserByJwt(jwt);
        List<Message> messages = messageService.findChatMessages(chatId);
        return messages;
    }
}
