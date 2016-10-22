var service = angular.module('DashboardService', []);
service.factory('DashboardTilesFactory', function($http) {
    var factory = {};
    factory.getTopViolatedMessages = function() {
    	$('#loadingTopViolatedMessages').show();
        return $http.get('/sonarreport/app/stats/topViolatedMessages').then(function(response) {
            return response.data;
        });
    }

    factory.getTopViolatingResources = function() {
    	$('#loadingViolatedResources').show();
        return $http.get("/sonarreport/app/stats/topViolatingResources/5").then(function(response) {
            return response.data;
        });
    }

    factory.getViolationsForResource = function(resourcePath) {
    	return $http.get("/sonarreport/app/stats/getViolationsForResource", {params: {"resourceName" : resourcePath}}).then(function (response){
    		return response.data;
    	});
    }
    
    factory.getViolationStats = function() {
    	return $http.get("/sonarreport/app/severities/").then(function(response){
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
    		
    		
    		
    		var severities = [];
    		var severity = {"name" : 'blocker', "count" : blockerCount, "percentage" : blockerPercentage};
    		severities.push(severity);
    		
    		severity = {"name" : 'critical', "count" : criticalCount, "percentage" : criticalPercentage};
    		severities.push(severity);
    		
    		severity = {"name" : 'major', "count" : majorCount, "percentage" : majorPercentage};
    		severities.push(severity);
    		
    		severity = {"name" : 'minor', "count" : minorCount, "percentage" : minorPercentage};
    		severities.push(severity);
    		
    		severity = {"name" : 'info', "count" : infoCount, "percentage" : infoPercentage};
    		severities.push(severity);
    		
    		return severities;
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
    
    factory.getStatusesOfViolations = function() {
    	return $http.get("/sonarreport/app/statuses/").then(function(response) {
        	var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
            return payLoad.data;
    	})
    }

    return factory;
});


service.factory('NavBarFactory', function($http) {
    var factory = {};
    factory.syncIssuesFromSonar = function() {
        return $http.get('/sonarreport/app/issues/sync').then(function(response) {
            return response.data;
        });
    }

    return factory;
});