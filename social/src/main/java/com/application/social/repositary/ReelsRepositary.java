package com.application.social.repositary;

import com.application.social.models.Reels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelsRepositary extends JpaRepository<Reels,Integer> {

    public List<Reels> findByUserId(Integer userId);

}
