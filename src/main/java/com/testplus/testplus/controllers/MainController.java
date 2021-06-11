package com.testplus.testplus.controllers;
import com.testplus.testplus.Utils;
import com.testplus.testplus.models.Test;
import com.testplus.testplus.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Test> tests = testRepository.findAll();
        Utils.getCurrentUserLogin();
        model.addAttribute("tests", tests);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "страница о нас");
        return "about";
    }

    @GetMapping("/search")
    public String searchedTests(@RequestParam String title,Model model){
        Iterable<Test> tests = testRepository.findAll();
        List<Test> needTests = new ArrayList<>();
        tests.forEach(it->{
            if (it.getTitle().contains(title)){
                needTests.add(it);
            }
        });
        model.addAttribute("tests",needTests.iterator());
        //testRepository.fin
        return "home";
    }
}