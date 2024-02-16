package com.application.social.repositary;

import com.application.social.models.Chat;
import com.application.social.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepositary extends JpaRepository<Chat, Integer> {
    List<Chat> findByUsersId(Integer userId);

    @Query("select c from Chat c where :user member of c.users and :reqUser member of c.users")
    Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
}
