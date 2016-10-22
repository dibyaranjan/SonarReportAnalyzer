package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.ResourceService;

@RestController
@RequestMapping(value = "resources")
public class ResourceController {
    private static final boolean SUCCESSFUL = true;
    
    @Autowired
    private ResourceService service;
    
    @RequestMapping("/")
    public GenericJsonObject getResource() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getAllResources());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/{resource}")
    public GenericJsonObject getIssuesForResource(@PathVariable("resource") int resourceId) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getIssuesForResource(resourceId));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/{resource}/{severity}")
    public GenericJsonObject getIssuesForResource(@PathVariable("resource") int resourceId,
            @PathVariable("severity") String severity) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getIssuesForResource(resourceId, severity));
        json.setSuccessful(SUCCESSFUL);
        return json;
    }
}
