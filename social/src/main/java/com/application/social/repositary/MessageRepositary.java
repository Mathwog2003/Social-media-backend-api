package com.application.social.repositary;

import com.application.social.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepositary  extends JpaRepository<Message,Integer> {
    public List<Message> findByChatId(Integer chatId);
}
