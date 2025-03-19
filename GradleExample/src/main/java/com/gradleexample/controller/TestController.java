package com.gradleexample.controller;

import com.gradleexample.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/home")
    public ResponseEntity<String> gethome(){
        return ResponseEntity.ok("hello world");
    }
    @GetMapping("/json")
    public ResponseEntity<Employee> getEmp(){

       return ResponseEntity.status(HttpStatus.OK)
               .header("info","Employee data")
               .body(new Employee(1,"farhaan",5600.9));
    }
}
