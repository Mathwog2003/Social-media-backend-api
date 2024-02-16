package com.application.social.repositary;

import com.application.social.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositary extends JpaRepository<Comment,Integer> {
}
