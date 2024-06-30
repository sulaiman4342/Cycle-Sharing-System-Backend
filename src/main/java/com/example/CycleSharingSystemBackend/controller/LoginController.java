package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.model.Login;
import com.example.CycleSharingSystemBackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/login")
public class LoginController {
    @Autowired
    private LoginService userService;

//    @GetMapping("/login")
//    public ModelAndView login(){
//        ModelAndView mav = new ModelAndView("login");
//        mav.addObject("user",new Login());
//        return mav;
//    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Login user){

        Login oauthUser = userService.login(user.getUsername(), user.getPassword());

        if(oauthUser == null){
            return new ResponseEntity<>("Invalid user name or password", BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("user Logged in Successfully", OK);
        }
    }
}
