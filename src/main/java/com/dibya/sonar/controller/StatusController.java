package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.StatusService;

@RestController
@RequestMapping(value = "statuses")
public class StatusController {
    private static final boolean SUCCESSFUL = true;
    
    @Autowired
    private StatusService service;
    
    @RequestMapping(value = "/")
    public GenericJsonObject getAllStatuesCount() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getAllStatusesCount());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/{status}/issues")
    public GenericJsonObject getIssuesByStatus(@PathVariable("status") String status) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getIssuesForStatus(status));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

}
