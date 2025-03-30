package com.java.club.camphoria.Java_Camphoria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")
public class HelloController {
    @GetMapping({"/greet","/"})
    public ResponseEntity<String> greet(){
        return new ResponseEntity<>("Hellow", HttpStatus.OK);
    }
}
