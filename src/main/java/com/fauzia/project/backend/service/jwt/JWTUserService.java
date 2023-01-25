package com.fauzia.project.backend.service.jwt;

import com.fauzia.project.backend.model.UserModel;
import com.fauzia.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JWTUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByUserName(username);

        if (userModel.isPresent()) {
            return new User(userModel.get().getUserId()+"-"+userModel.get().getUserName(), userModel.get().getUserPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
