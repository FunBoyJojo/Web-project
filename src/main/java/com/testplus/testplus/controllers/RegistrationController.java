package com.testplus.testplus.controllers;

import com.testplus.testplus.models.Role;
import com.testplus.testplus.models.User;
import com.testplus.testplus.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/blog/login")
    public String lohinPage(Model model) {
        return "/login";
    }

    @GetMapping("/blog/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/blog/registration")
    public String addUser(User user, String urole, Map<String, Object> model) {
        User userFromDb = userRepo.findByLogin(user.getLogin());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        if(urole.equals("teacher")){
            user.setRoles(Collections.singleton(Role.TEACHER));
        }else {
            user.setRoles(Collections.singleton(Role.USER));
        }
        user.setActive(true);

        userRepo.save(user);

        return "redirect:/blog/login";
    }

}
