package com.application.social.controller;

import com.application.social.config.JwtProvider;
import com.application.social.models.User;
import com.application.social.repositary.UserRepositary;
import com.application.social.request.LoginRequest;
import com.application.social.response.AuthResponse;
import com.application.social.service.CustomUsersDetailsService;
import com.application.social.service.UserServiceimplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserServiceimplementation userServiceimplementation;
    @Autowired
    private UserRepositary userRepositary;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;
    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {

        User isExist = userRepositary.findByEmail(user.getEmail());
        if(isExist!=null)
        {
            throw new Exception("This email is aldredy used with another account");
        }
        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepositary.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token,"Register Success");
        return res;
    }
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token,"Login Success");
        return res;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUsersDetailsService.loadUserByUsername(email);
        if(userDetails==null)
        {
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw  new BadCredentialsException("password not matched");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
