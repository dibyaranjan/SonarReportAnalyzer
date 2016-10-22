var service = angular.module('SeveritySummaryService', []);
service.factory('SeveritySummaryFactory', function($http) {
    var factory = {};
    
    factory.getViolationStats = function() {
    	return $http.get("/sonarreport/app/severities/open").then(function(response){
    		var stats = {};
    		var payLoad = response.data;
    		if (!payLoad.successful) {
    			return stats;
    		}
    		
    		var infoCount = 0;
    		var minorCount = 0;
    		var majorCount = 0;
    		var criticalCount = 0;
    		var blockerCount = 0;
    		
    		var severities = payLoad.data;
    		$.each(severities, function(index, severity) {
    			if (severity.label == "INFO") {
    				infoCount = severity.count;
    			} else if (severity.label == "MINOR") {
    				minorCount = severity.count;
    			} else if (severity.label == "MAJOR") {
    				majorCount = severity.count;
    			} else if (severity.label == "CRITICAL") {
    				criticalCount = severity.count;
    			} else if (severity.label == "BLOCKER") {
    				blockerCount = severity.count;
    			}
    		});
    		
    		var totalCount = infoCount + minorCount + majorCount + criticalCount + blockerCount;
    		var infoPercentage = ((infoCount / totalCount) * 100).toFixed(2);
    		var minorPercentage = ((minorCount / totalCount) * 100).toFixed(2);
    		var majorPercentage = ((majorCount / totalCount) * 100).toFixed(2);
    		var criticalPercentage = ((criticalCount / totalCount) * 100).toFixed(2);
    		var blockerPercentage = ((blockerCount / totalCount) * 100).toFixed(2);
    		
    		
    		
    		var severitiesStats = [];
    		var severity = {"name" : 'blocker', "count" : blockerCount, "percentage" : blockerPercentage};
    		severitiesStats.push(severity);
    		
    		severity = {"name" : 'critical', "count" : criticalCount, "percentage" : criticalPercentage};
    		severitiesStats.push(severity);
    		
    		severity = {"name" : 'major', "count" : majorCount, "percentage" : majorPercentage};
    		severitiesStats.push(severity);
    		
    		severity = {"name" : 'minor', "count" : minorCount, "percentage" : minorPercentage};
    		severitiesStats.push(severity);
    		
    		severity = {"name" : 'info', "count" : infoCount, "percentage" : infoPercentage};
    		severitiesStats.push(severity);
    		
    		return severitiesStats;
    	});
    }
    
    factory.getViolationsBySeverity = function(severity) {
    	return $http.get('/sonarreport/app/severities/'+severity+'/issues').then(function(response) {
    		var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
    		return payLoad.data;
    	});
    }
    
    factory.getResourcesBySeverity = function(severity) {
    	return $http.get('/sonarreport/app/severities/'+severity+'/resources').then(function(response) {
    		var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
    		return payLoad.data;
    	});
    }
    
    factory.getIssueOfResourceBySeverity = function(resourceId, severity) {
    	return $http.get('/sonarreport/app/resources/'+resourceId+'/'+severity).then(function(response) {
    		var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
    		return payLoad.data;
    	});
    }

    return factory;
});