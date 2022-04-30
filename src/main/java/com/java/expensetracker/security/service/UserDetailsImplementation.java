package com.java.expensetracker.security.service;

import com.java.expensetracker.model.BudgetTrackerUser;
import com.java.expensetracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BudgetTrackerUser user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getUsername(), user.getPassword(),authorities);
    }
}
