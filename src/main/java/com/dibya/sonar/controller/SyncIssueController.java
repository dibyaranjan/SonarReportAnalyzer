package com.dibya.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.sync.SonarReportSynchronizer;

/**
 * 
 * 
 * @author Dibya Ranjan
 */
@RestController
@RequestMapping(value = "sync")
public class SyncIssueController {
    @Autowired(required = false)
    private SonarReportSynchronizer synchronizer;

    @RequestMapping(value = "issues", method = RequestMethod.GET)
    public GenericJsonObject syncIssues() {
        boolean sync = synchronizer.sync();

        GenericJsonObject json = new GenericJsonObject();
        json.setSuccessful(sync);

        return json;
    }
}
