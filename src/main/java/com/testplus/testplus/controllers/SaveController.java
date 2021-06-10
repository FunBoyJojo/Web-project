package com.testplus.testplus.controllers;

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
        Iterable<PassedTests> savedTests = savePassedTests.findAll();
        model.addAttribute("savedtests", savedTests);
        return "saved-passed-tests";
    }

    @PostMapping("/blog/passed-tests")
    public String saveResult(/*@ModelAttribute Test test, @ModelAttribute String score, */Model model)
    {
     /*   Optional<Test> t = testRepository.findById(2);
        Optional<User> user = userRepository.findById(1);
        ArrayList<Test> res = new ArrayList<>();
        ArrayList<User> ures = new ArrayList<>();

        t.ifPresent(res::add);
        user.ifPresent(ures::add);

        PassedTests passedTests = new PassedTests();
        passedTests.setTest(res.get(0));
        passedTests.setResult(Double.parseDouble("40"));
        passedTests.setUser(ures.get(0));
        savePassedTests.save(passedTests);*/

        return "redirect:/";
    }


}
