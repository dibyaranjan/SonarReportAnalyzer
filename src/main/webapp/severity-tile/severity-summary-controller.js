var app = angular.module('SeveritySummary', ['dashboard']);

app.controller('SeveritySummaryController', function($scope, SeveritySummaryFactory) {
	SeveritySummaryFactory.getViolationStats().then(function (severities){
		$scope.severities = severities;
	});
	
	$scope.getAllIssuesFor = function(resource, severity) {
		$scope.currentResource = resource.resourceName;
		SeveritySummaryFactory.getIssueOfResourceBySeverity(resource.resourceId, severity).then(function(issues) {
			$scope.violationsInReport = issues;
			
			$scope.currentIssuesPage = 1;
			$scope.totalIssuesPages = Math.max(1, Math.floor(issues.length / 10));
    		
    		$scope.previousPage = function() {
    			$scope.currentIssuesPage--;
    		}
    	    
    	    $scope.nextPage = function() {  	
    			$scope.currentIssuesPage++;
    		}
		});
	}
	
	
	$scope.listResourcesForSeverity = function(severity) {
		$scope.currentSeverity = severity;
		SeveritySummaryFactory.getResourcesBySeverity(severity).then(function(resources) {
			$scope.resources = resources;
			$scope.getAllIssuesFor(resources[0], severity);
		});
	}
	
	$scope.getIssuesForResource = function() {
		SeveritySummaryFactory.getViolationsBySeverity(severity).then(function(issues) {
			$scope.filteredIssues = issues;
		});
	}
}).directive("severitySummary", function() {
	return {
		restrict : 'E',
		templateUrl : 'severity-tile/severitySummary.html'
	}
});
