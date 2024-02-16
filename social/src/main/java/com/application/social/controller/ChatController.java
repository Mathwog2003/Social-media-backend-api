package com.application.social.controller;

import com.application.social.models.Chat;
import com.application.social.models.User;
import com.application.social.repositary.ChatRepositary;
import com.application.social.request.CreateChatRequest;
import com.application.social.service.ChatService;
import com.application.social.service.ChatServiceInterface;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserServiceimplementation userServiceimplementation;

    @PostMapping("/api/chats/")
    public Chat createChat(@RequestHeader("Authorization") String jwt,@RequestBody CreateChatRequest req) throws Exception {
        User reqUser = userServiceimplementation.findUserByJwt(jwt);
        Optional<User> user2 = userServiceimplementation.findUserById(req.getUserId());
        Chat chat = chatService.createChat(reqUser,user2.get());
        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt)
    {
        User user = userServiceimplementation.findUserByJwt(jwt);
        List<Chat> chats = chatService.findUsersChat(user.getId());
        return chats;
    }
}
