package com.pablopronsky.eggnews.services;

import com.pablopronsky.eggnews.entities.Image;
import com.pablopronsky.eggnews.entities.UserEntity;
import com.pablopronsky.eggnews.enums.Role;
import com.pablopronsky.eggnews.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Transactional
    public void register(String name, String email, String password, String password2, MultipartFile file) throws Exception{
        validate(name, email, password, password2);
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);
        user.setEmail(email);
        Image image = imageService.saveImage(file);
        user.setImage(image);
        userRepository.save(user);
    }

    @Transactional
    public void update(String name, String email, String password, String password2, Long id, MultipartFile file) throws Exception{
        validate(name, email, password, password2);
        Optional<UserEntity> imageOptional = userRepository.findById(id);
        if (imageOptional.isPresent()){
            UserEntity user = imageOptional.get();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRole(Role.USER);
            Long idImage = null;
            if(user.getImage() != null){
                idImage = user.getImage().getId();
            }
            Image image = imageService.updateImage(file, idImage);
            user.setImage(image);
            userRepository.save(user);
        }
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
        UserEntity user = userRepository.searchByEmail(email);

        if(user != null){
            List<GrantedAuthority> permissions = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE" + user.getRole().toString());
            permissions.add(grantedAuthority);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usersession", user);

            return new User(user.getEmail(),user.getPassword(), permissions);

        }
        return null;
}
}
