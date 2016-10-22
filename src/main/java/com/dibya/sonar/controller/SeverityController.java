package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.SeverityService;

@RestController
@RequestMapping(value = "severities")
public class SeverityController {
    private static final boolean SUCCESSFUL = true;
    @Autowired
    private SeverityService service;

    @RequestMapping("/{status}")
    public GenericJsonObject getAllSeverities(@PathVariable("status") String status) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getSeverityCounts(status));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
    
    @RequestMapping("/{severity}/resources")
    public GenericJsonObject getResourcesForSeverity(@PathVariable("severity") String severity) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getResourcesForSeverity(severity));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
    
    @RequestMapping("/{severity}/issues")
    public GenericJsonObject getIssuesForSeverity(@PathVariable("severity") String severity) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getAllIssueForSeverity(severity));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
    
}
