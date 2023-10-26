package com.pablopronsky.eggnews.services;

import com.pablopronsky.eggnews.entities.User;
import com.pablopronsky.eggnews.enums.Role;
import com.pablopronsky.eggnews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public void register(String name, String email, String password, String password2) throws Exception{

        validate(name, email, password, password2);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(Role.USER);
        user.setEmail(email);
        userRepository.save(user);
    }

    private void validate(String name, String email, String password, String password2) throws Exception{
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null");
        if(email == null || email.isEmpty()) throw new IllegalArgumentException("Email cannot be null");
        if(password == null || password.isEmpty() || password.length() <= 5){
            throw new IllegalArgumentException("Password can't be empty and should be longer than 5 characters");
        }
        if(!password.equals(password2)) throw new IllegalArgumentException("Both passwords should be the same");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.searchByEmail(email);

        if(user != null){
            List<GrantedAuthority> permissions = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE" + user.getRole().toString());
            permissions.add(grantedAuthority);

            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), permissions);
        }
        return null;
    }
}
