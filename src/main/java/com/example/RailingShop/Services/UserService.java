package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(User user){
        User activeUser=userRepository.findByUsername(user.getUsername());
        if(activeUser!=null){
            throw new RuntimeException("Потребител с това потребителско име вече съществува!");
        }
        String encryptedPassword= BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public void updateUserInformation(User user){
        userRepository.save(user);
    }

    public User findById(Long userId){
        return userRepository.findById(userId).get();
    }
}
