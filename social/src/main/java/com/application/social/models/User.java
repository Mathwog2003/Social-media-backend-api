package com.application.social.models;

import com.application.social.models.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    private List<Integer> followers = new ArrayList<>();

    private List<Integer> followings = new ArrayList<>();
    @ManyToMany
    @JsonIgnore
//    @JoinTable(
//            name = "users_saved_post",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "saved_post_id"))
    private List<Post> savedPost = new ArrayList<>();
}
