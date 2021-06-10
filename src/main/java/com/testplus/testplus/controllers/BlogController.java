package com.testplus.testplus.controllers;

import com.testplus.testplus.Utils;
import com.testplus.testplus.models.*;
import com.testplus.testplus.repo.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QueryRepository queryRepository;
    @Autowired
    private AnswerRepository answerRepository;
@Autowired
private PassedTestsRepository passedTestsRepository;


    @Value("${upload.path}")
    private String uploadPath;

    String testTitle = "";
    int currentTestId = 0;

    @GetMapping("/blog/create-test")
    public String createTest(Model model) {
        return "create-test";
    }

    @PostMapping("/blog/create-test")
    public String addTestTitle(@RequestParam String title,@RequestParam String time_h, @RequestParam String time_m,@RequestParam String countOfPass,
                               Model model){
        long time = Utils.timeConvertor(time_h,time_m);
        Test test = new Test(title, time, Integer.parseInt(countOfPass));
        testTitle=title;
        testRepository.save(test);
        currentTestId = test.getId();
        return "redirect:/blog/addQuery";
    }

    @GetMapping("/blog/addQuery")
    public String addQuery(Model model) {

        model.addAttribute("title",testTitle);
        return "addQuery";
    }

    @PostMapping("/blog/addQuery")
    public String addQuery(@RequestParam("img") MultipartFile img, @RequestParam String title, @RequestParam String answer_text_1, @RequestParam(name = "answer_istrue_1", required = false, defaultValue = "false")boolean answer_istrue_1,
                           @RequestParam String answer_text_2, @RequestParam(name = "answer_istrue_2", required = false, defaultValue = "false")boolean answer_istrue_2,
                           @RequestParam String answer_text_3, @RequestParam(name = "answer_istrue_3", required = false, defaultValue = "false")boolean answer_istrue_3,
                           @RequestParam String answer_text_4, @RequestParam(name = "answer_istrue_4", required = false, defaultValue = "false")boolean answer_istrue_4,
                           Model model) throws IOException {

        Optional<Test> t = testRepository.findById(currentTestId);
        ArrayList<Test> res = new ArrayList<>();
        t.ifPresent(res::add);
        Query query = new Query(title,res.get(0));

        if (img != null && !img.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + img.getOriginalFilename();

            img.transferTo(new File("C:/"+ uploadPath + "/" + resultFilename));

            query.setFileName(resultFilename);
        }
//        else{
//            query.setFileName("default");
//        }


        Answer a1 = new Answer(answer_text_1,answer_istrue_1);
        Answer a2 = new Answer(answer_text_2,answer_istrue_2);
        Answer a3 = new Answer(answer_text_3,answer_istrue_3);
        Answer a4 = new Answer(answer_text_4,answer_istrue_4);

        a1.setQuery(query);
        a2.setQuery(query);
        a3.setQuery(query);
        a4.setQuery(query);


        query.setAnswers(new ArrayList<>());
        query.getAnswers().add(a1);
        query.getAnswers().add(a2);
        query.getAnswers().add(a3);
        query.getAnswers().add(a4);
        res.get(0).getQuerys().add(query);
       // testRepository.save(res.get(0));
        testRepository.save(res.get(0));
        return "redirect:/blog/addQuery";
    }
    Test currentTest;

    @GetMapping("/blog/passing-tests/{id}")
    public String passingTests(@PathVariable(value = "id") int id, Model model) {
        queries  = new ArrayList<>();
        Optional<Test> opTest = testRepository.findById(id);
        ArrayList<Test> res = new ArrayList<>();
        opTest.ifPresent(res::add);
        currentTest = res.get(0);

        Utils.randomQueries(currentTest);
//        if(currentTest.getQuerys().get(0).getFileName()==null){
////            currentTest.getQuerys().get(0).setFileName("");
////        }
        model.addAttribute("query",currentTest.getQuerys().get(0));
        model.addAttribute("test",currentTest);
        model.addAttribute("time",currentTest.getTime()/60000);//todo to min
        index = 0;
        return "passing-tests";
    }

    List<List<Boolean> > queries;

    int index;

    @GetMapping("/blog/passing-tests")
    public String passingTests(@RequestParam(name = "a1", required = false, defaultValue = "false")boolean a1,
                               @RequestParam(name = "a2", required = false, defaultValue = "false")boolean a2,
                               @RequestParam(name = "a3", required = false, defaultValue = "false")boolean a3,
                               @RequestParam(name = "a4", required = false, defaultValue = "false")boolean a4,
                               @RequestParam(name = "time") String time,
                               Model model) {
        index++;
        List<Boolean> tmpAnswers = new ArrayList<>();
        tmpAnswers.add(a1);
        tmpAnswers.add(a2);
        tmpAnswers.add(a3);
        tmpAnswers.add(a4);
        queries.add(tmpAnswers);
//        if(currentTest.getQuerys().get(index).getFileName()==null){
//            currentTest.getQuerys().get(index).setFileName("");
//        }
        if(currentTest.getQuerys().size()==index || Integer.parseInt(time) < 0){

            String score  = Utils.totalScore(currentTest,queries);
            model.addAttribute("test",currentTest);
            model.addAttribute("score",score);

           //todo если у юзера есть этот тестпайст, гетим его , сохраняем больший процент,
            //todo уменьшаем количество попыток
            //todo , если нет создаем новы, оличество попыток берем из теста, уменьшаем на один, сохраняем в его и скор в тестпейсст
            PassedTests passedTest = new PassedTests();
            //passedTest.setUser();
            passedTest.setTest(currentTest);
            passedTest.setTime(Integer.parseInt(time));
            passedTest.setResult(Double.parseDouble(score));
            //passedTestsRepository.save(passedTest);

            return "passed-tests";
        }

        model.addAttribute("id",currentTest.getId());
        model.addAttribute("index",index);
        model.addAttribute("query",currentTest.getQuerys().get(index));
        model.addAttribute("time",Integer.parseInt(time));
        return "passing-tests";
    }

     /*Добавить в модель тест поля для ввода кол-ва попыток и время на тест!!!!!!!!!!!!!!!!!!!!!!!!
    еще бы сделать по возможности отдельный тест где можно вставить картинку и вписать ответ. Сделать рандомный набор вопросов
    */
    /*    @Autowired
    private UserRepo userRepo;*/

   /*

    @PostMapping("/blog/registration")
    public String addUser(User user, Map<String, Object> model) {
       // User byUsername = userRepo.find(user.getLogin());
//
//        if (byUsername != null){
//            model.put("user","exist");
//            return "blog/registration";
//        }
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        userRepo.save(user);
        return "redirect:/blog/login";
    }*/

    /*    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "/blog-main";//here was blog-main
    }*/

/*    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

   @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }*/

/*    @PostMapping("/blog/passing-tests")
    public String passingTestsCheck(@ModelAttribute("test") Test userResponseTest, Model model) {
        //Optional<Test> post = testRepository.findById(userResponseTest.getId());
        Optional<Test> post = testRepository.findById(tmpTestId);
        ArrayList<Test> res = new ArrayList<>();
        post.ifPresent(res::add);
        Test realTest = res.get(0);
        double score  = Utils.totalScore(realTest,userResponseTest);
        //todo save to user,passed-test and show user result

        return "passing-tests";
    }*/

/*    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }*/
}
