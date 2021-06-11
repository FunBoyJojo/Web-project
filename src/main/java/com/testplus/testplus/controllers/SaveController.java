package com.testplus.testplus.controllers;

import com.testplus.testplus.Utils;
import com.testplus.testplus.models.PassedTests;
import com.testplus.testplus.models.Test;
import com.testplus.testplus.models.User;
import com.testplus.testplus.repo.PassedTestsRepository;
import com.testplus.testplus.repo.TestRepository;
import com.testplus.testplus.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SaveController {

    @Autowired
    private PassedTestsRepository savePassedTests;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/blog/saved-passed-tests")
    public String pageOfPassedTests(Model model) {
        User user = userRepository.findByLogin(Utils.getCurrentUserLogin());
        model.addAttribute("savedtests",user.getPassedtests().iterator());
        return "saved-passed-tests";
    }

}
