package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.ViolationService;

@RestController
@RequestMapping("violations")
public class ViolationController {
    private static final boolean SUCCESSFUL = true;
    
    @Autowired
    private ViolationService violationService;
    
    @RequestMapping(value = "/")
    public GenericJsonObject getIssuesForStatus() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(violationService.getAllViolations());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
    
    @RequestMapping(value = "/{status}")
    public GenericJsonObject getIssuesForStatus(@PathVariable("status") String status) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(violationService.getViolationsWithStatus(status));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/{status}/")
    public GenericJsonObject getIssuesForStatus(@PathVariable("status") String status, @RequestParam("orderBy") String orderBy) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(null);
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

}
