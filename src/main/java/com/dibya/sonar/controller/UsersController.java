package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UserService service;
    
    @RequestMapping("/")
    public GenericJsonObject getUserList() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getUsersWithViolationCount());
        json.setSuccessful(true);
        return json;
    }
    
    @RequestMapping("/{userId}/issues")
    public GenericJsonObject getIssuesForUser(@PathVariable("userId") String userId) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getViolationsForUser(userId));
        json.setSuccessful(true);
        return json;
    }
}
