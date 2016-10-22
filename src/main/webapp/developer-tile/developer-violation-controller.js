/**
 * 
 */
var app = angular.module('DeveloperViolations', []);

app.controller('DeveloperViolationController', function($scope, DeveloperSummaryFactory) {
	DeveloperSummaryFactory.getViolationsForAllDevelopers().then(function(violations) {
        $scope.violationsForAllDevelopers = violations;
    });
	
	$scope.showIssuesForDeveloper = function(user) {
		console.log(user);
	}
});