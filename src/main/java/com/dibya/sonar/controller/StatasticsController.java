package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.StatasticsService;

@RestController
@RequestMapping(value = "/stats")
public class StatasticsController {
    private static final boolean SUCCESSFUL = true;
    
    @Autowired
    private StatasticsService statService;

    @RequestMapping(value = "/topViolatedMessages")
    public GenericJsonObject getTopViolatedMessages() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getTopViolatedMessages());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/topViolatingResources/{limit}")
    public GenericJsonObject getTopViolatingResources(@PathVariable(value = "limit") int countLimit) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getTopViolatedResourcesWithLimit(countLimit));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/violationsForAllDevelopers")
    public GenericJsonObject getViolationsForAllDevelopers() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getViolationsForAllDevelopers());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/getViolationCountBySeverity")
    public GenericJsonObject getViolationCountBySeverity() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getViolationCountBySeverity());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/getViolationsForResource", method = RequestMethod.GET)
    public GenericJsonObject getViolationsForResource(String resourceName) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getViolationsForResource(resourceName));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/getRecentViolations")
    public GenericJsonObject getRecentViolations() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getRecentViolations());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/getAllBlameDetails")
    public GenericJsonObject getAllViolationDetails() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(statService.getAllViolations());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
}
