package com.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/haha")
public class hello {
    @GetMapping("/{id}")
    public String name(@PathVariable Integer id){
        System.out.println("jack");
        System.out.println("jack");
        System.out.println("jack");
        System.out.println("id:"+id);
        return "hello";
    }
}
