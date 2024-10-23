package com.vitalstats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.vitalstats.service.SendEmailService;

@Controller
public class Demo {

    @Autowired
    private SendEmailService sendEmailService;

    @GetMapping("/")
    public ResponseEntity<?> Index(){
        return  ResponseEntity.ok("hello World");
    }

    @PostMapping("sendemail")
    public ResponseEntity<?> sendEmail( ){
        sendEmailService.sendEmail("lalitesh.mupparaju04@gmail.com","Forget Password","Hello World");
        return  ResponseEntity.ok("hello World");
    }

}

