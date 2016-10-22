package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.StatasticsService;
import com.dibya.sonar.service.sync.SonarReportSynchronizer;

@RestController
@RequestMapping("issues")
public class IssueController {
    private static final boolean SUCCESSFUL = true;

    @Autowired
    private StatasticsService service;

    @Autowired
    private SonarReportSynchronizer synchronizer;

    @RequestMapping(value = "/")
    public GenericJsonObject getAllIssues() {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getAllViolations());
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/{key}")
    public GenericJsonObject getAllIssues(String key) {
        GenericJsonObject json = new GenericJsonObject();
        json.setData(service.getAllViolations()); // TODO : Implement Service
        json.setSuccessful(SUCCESSFUL);
        return json;
    }

    @RequestMapping(value = "/sync")
    public GenericJsonObject syncIssues() {
        boolean sync = synchronizer.sync();

        GenericJsonObject json = new GenericJsonObject();
        json.setSuccessful(sync);

        return json;
    }
}
